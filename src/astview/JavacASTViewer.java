package astview;

import java.net.URI;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.tree.EndPosTable;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.Context;

import astview.listener.ListenerMix;
import astview.provider.ViewContentProvider;
import astview.provider.ViewLabelProvider;


public class JavacASTViewer extends ViewPart {
	
	public static final String ID = "javacastviewer";
	
	private TreeViewer fViewer;
	
	private ListenerMix fSuperListener;

	
	private Action fRefreshAction;
	private Action fCollapseAction;
	private Action fExpandAction;
	private Action fDoubleClickAction;
	
	private IDocument fCurrentDocument;
	private ITextEditor fEditor;
	
	URI uri;

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.setSite(site);
		if (fSuperListener == null) {
			fSuperListener= new ListenerMix(this);
		}
	}

	public void createPartControl(Composite parent) {
		
		fViewer = new TreeViewer(parent, SWT.SINGLE);
		fViewer.setLabelProvider(new ViewLabelProvider());
		fViewer.setContentProvider(new ViewContentProvider());
//		fViewer.setContentProvider(new ViewTestContentProvider());
//		fViewer.setInput(getSite());
		fViewer.addDoubleClickListener(fSuperListener);
		
		makeActions();
		contributeToActionBars();
//		getSite().setSelectionProvider(new ASTViewSelectionProvider(fViewer));
		
		try {
			IEditorPart part= EditorUtility.getActiveEditor();
			if (part instanceof ITextEditor) {
				setInput((ITextEditor) part);
			}
		} catch (CoreException e) {
			// ignore
		}
	}

	public void setFocus() {
		// not supported
	}
	
	// ------------------------------------------------------------------------------------
	
	private void makeActions() {
		fRefreshAction = new Action() {
			@Override
			public void run() {
				try {
					refreshAST();
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		};
		fRefreshAction.setText("&Refresh AST"); 
		fRefreshAction.setToolTipText("Refresh AST"); 
		fRefreshAction.setEnabled(false);
		ASTViewImages.setImageDescriptors(fRefreshAction, ASTViewImages.REFRESH);
		
		fCollapseAction = new Action() {
			@Override
			public void run() {
				performCollapse();
			}
		};
		fCollapseAction.setText("C&ollapse"); 
		fCollapseAction.setToolTipText("Collapse Selected Node"); 
		fCollapseAction.setEnabled(true);
		ASTViewImages.setImageDescriptors(fCollapseAction, ASTViewImages.COLLAPSE);

		fExpandAction = new Action() {
			@Override
			public void run() {
				performExpand();
			}
		};
		fExpandAction.setText("E&xpand"); 
		fExpandAction.setToolTipText("Expand Selected Node"); 
		fExpandAction.setEnabled(true);
		ASTViewImages.setImageDescriptors(fExpandAction, ASTViewImages.EXPAND);
		
		fDoubleClickAction = new Action() {
			@Override
			public void run() {
				performDoubleClick();
			}
		};

	}
	
	private void refreshAST() throws CoreException {
		internalSetInput(uri);
	}

	protected void performCollapse() {
		IStructuredSelection selection= (IStructuredSelection) fViewer.getSelection();
		if (selection.isEmpty()) {
			fViewer.collapseAll();
		} else {
			fViewer.getTree().setRedraw(false);
			for (Object s : selection.toArray()) {
				fViewer.collapseToLevel(s, AbstractTreeViewer.ALL_LEVELS);
			}
			fViewer.getTree().setRedraw(true);
		}
	}

	protected void performExpand() {
		IStructuredSelection selection= (IStructuredSelection) fViewer.getSelection();
		if (selection.isEmpty()) {
			fViewer.expandToLevel(3);
		} else {
			fViewer.getTree().setRedraw(false);
			for (Object s : selection.toArray()) {
				fViewer.expandToLevel(s, AbstractTreeViewer.ALL_LEVELS);
			}
			fViewer.getTree().setRedraw(true);
		}
	}
	
	protected void performDoubleClick() {
		if (fEditor == null) {
			return;
		}

		ISelection selection = fViewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();
		if(obj!=null && obj instanceof JavacASTNode) {
			JavacASTNode node = (JavacASTNode)obj;
			EditorUtility.selectInEditor(fEditor, node.getStartpos(),node.getEndpos()-node.getStartpos());
		}
	}
	
	// ------------------------------------------------------------------------------------
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		bars.getToolBarManager().add(fRefreshAction);
		bars.getToolBarManager().add(fCollapseAction);
		bars.getToolBarManager().add(fExpandAction);
	}

	
	public void setInput(ITextEditor editor) throws CoreException {
		if (editor != null) {
			fEditor = editor;
			uri = EditorUtility.getURI(editor);
			internalSetInput(uri);
			installModificationListener();
		}
	}
	
	// 1、点击刷新按钮时会调用
	// 2、在启动视图时，也就是在createPartControl()方法中会间接调用
	private JCCompilationUnit internalSetInput(URI is) throws CoreException {
		JCCompilationUnit root = null;
		try {
			root= createAST(is);
			resetView(root);
			if (root == null) {
				setContentDescription("AST could not be created.");
				return null;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return root;
	}
	
	
//	ParserFactory pf = null;
//	{
//		Context context = new Context();
//		JavacFileManager.preRegister(context);
//		pf = ParserFactory.instance(context);
//	}
	
	public static EndPosTable ept = null;
	
	private JCCompilationUnit createAST(URI is) {
		Context context = new Context();
		JavacFileManager.preRegister(context);
		JavaFileManager fileManager = context.get(JavaFileManager.class);
		JavaCompiler comp = JavaCompiler.instance(context);
		JavacFileManager dfm = (JavacFileManager) fileManager;

		JavaFileObject jfo = dfm.getFileForInput(is.getPath());
		comp.genEndPos = true;
		JCCompilationUnit tree = comp.parse(jfo);
		ept = tree.endPositions;
		
//		CharSequence seq = java.nio.CharBuffer.wrap(is.toCharArray());
//		Parser parser = pf.newParser(is,false, false, false);
//		JCCompilationUnit  tree = parser.parseCompilationUnit();

//		com.sun.tools.javac.util.List<JavaFileObject> otherFiles = com.sun.tools.javac.util.List.nil();
//		for (JavaFileObject fo : dfm.getJavaFileObjects(new File(is)))
//			otherFiles = otherFiles.prepend(fo);
//		com.sun.tools.javac.util.List<JCTree.JCCompilationUnit> list = comp.parseFiles(otherFiles);

		return tree;
	}
	
	private void resetView(JCCompilationUnit root) {
		fViewer.setInput(root);
//		fViewer.getTree().setEnabled(root != null);
//		fSash.setMaximizedControl(fViewer.getTree());
		setASTUptoDate(root != null);
	}

	// -----------------handle start---------------------------------------------------
	
	public void handleDoubleClick() {
		fDoubleClickAction.run();
	}
	
	public void handleDocumentChanged() {
		setASTUptoDate(false);
	}
	
	// -----------------handle end---------------------------------------------------

	
	private void setASTUptoDate(boolean isuptoDate) {
		fRefreshAction.setEnabled(!isuptoDate && uri != null);
	}
	
	
	private void installModificationListener() {
		fCurrentDocument= fEditor.getDocumentProvider().getDocument(fEditor.getEditorInput());
		fCurrentDocument.addDocumentListener(fSuperListener);
	}
	
	private void uninstallModificationListener() {
		if (fCurrentDocument != null) {
			fCurrentDocument.removeDocumentListener(fSuperListener);
			fCurrentDocument= null;
		}
	}

	public void handleDocumentDisposed() {
		uninstallModificationListener();
	}
	
	
	@Override
	public void dispose() { // 当关闭视图时会调用这个方法
		if (fSuperListener != null) {
			if (fEditor != null) {
				uninstallModificationListener();
			}
			fSuperListener.dispose(); // removes reference to view
			fSuperListener= null;
		}
		super.dispose();
	}
	
}



