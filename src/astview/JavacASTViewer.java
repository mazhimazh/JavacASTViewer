package astview;

import java.io.File;
import java.net.URI;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.parser.Parser;
import com.sun.tools.javac.parser.ParserFactory;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.Context;

import astview.listener.ListenerMix;
import astview.provider.ASTViewSelectionProvider;
import astview.provider.ViewContentProvider;
import astview.provider.ViewLabelProvider;
import astview.test.ViewTestContentProvider;


public class JavacASTViewer extends ViewPart {
	
	public static final String ID = "javacastviewer";
	
	private TreeViewer fViewer;
	private SashForm fSash;
	
	private ListenerMix fSuperListener;

	
	private Action fRefreshAction;
	private Action fFocusAction;
	
	private IDocument fCurrentDocument;
	private ITextEditor fEditor;
	
	URI is;

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.setSite(site);
//		if (fSuperListener == null) {
//			fSuperListener= new ListenerMix(this);
//
//			// 下面是什么意思？？
//			ISelectionService service= site.getWorkbenchWindow().getSelectionService();
//			service.addPostSelectionListener(fSuperListener);
//			site.getPage().addPartListener(fSuperListener);
//			FileBuffers.getTextFileBufferManager().addFileBufferListener(fSuperListener);
//		}
	}

	public void createPartControl(Composite parent) {
//		fSash= new SashForm(parent, SWT.VERTICAL | SWT.SMOOTH);
//		fViewer = new TreeViewer(fSash, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		
		fViewer = new TreeViewer(parent, SWT.SINGLE);
		
		fViewer.setLabelProvider(new ViewLabelProvider());
		fViewer.setContentProvider(new ViewContentProvider());
//		fViewer.setContentProvider(new ViewTestContentProvider());
//		fViewer.setInput(getSite());
//		fViewer.addSelectionChangedListener(fSuperListener);
//		fViewer.addDoubleClickListener(fSuperListener);
		
//		makeActions();
//		contributeToActionBars();
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
		
		
		fFocusAction = new Action() {
			@Override
			public void run() {
				performSetFocus();
			}
		};
		fFocusAction.setText("&Show AST of active editor"); 
		fFocusAction.setToolTipText("Show AST of active editor"); 
		fFocusAction.setActionDefinitionId(IWorkbenchCommandConstants.FILE_REFRESH);
		ASTViewImages.setImageDescriptors(fFocusAction, ASTViewImages.SETFOCUS);
	}
	
	protected void performSetFocus() {
		IEditorPart part= EditorUtility.getActiveEditor();
		if (part instanceof ITextEditor) {
			try {
				setInput((ITextEditor) part);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(fFocusAction);
		manager.add(fRefreshAction);
	}
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
		bars.setGlobalActionHandler(ActionFactory.REFRESH.getId(), fFocusAction);
	}

	private void refreshAST() throws CoreException {
		internalSetInput(is);
		System.out.println("你点击了刷新按钮！！！");
	}

	public void setInput(ITextEditor editor) throws CoreException {
		if (editor != null) {
			fEditor = editor;
			is = EditorUtility.getURI(editor);
			internalSetInput(is);
//			installModificationListener();
		}
	}
	
	
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
	
	
//	
//	ParserFactory pf = null;
//	{
//		Context context = new Context();
//		JavacFileManager.preRegister(context);
//		pf = ParserFactory.instance(context);
//	}
	
	JavacFileManager dfm = null;
	JavaCompiler comp = null;
	private JCCompilationUnit createAST(URI is) {
		if (comp == null) {
			Context context = new Context();
			JavacFileManager.preRegister(context);
			JavaFileManager fileManager = context.get(JavaFileManager.class);
			comp = JavaCompiler.instance(context);
			dfm = (JavacFileManager) fileManager;
		}
		
		
		JavaFileObject jfo = dfm.getFileForInput(is.getPath());
		JCCompilationUnit tree = comp.parse(jfo);
//		CharSequence seq = java.nio.CharBuffer.wrap(is.toCharArray());
//		 Parser parser = pf.newParser(is,false, false, false);
//		 JCCompilationUnit  tree = parser.parseCompilationUnit();
		

//		com.sun.tools.javac.util.List<JavaFileObject> otherFiles = com.sun.tools.javac.util.List.nil();
//		for (JavaFileObject fo : dfm.getJavaFileObjects(new File(is)))
//			otherFiles = otherFiles.prepend(fo);
//
//		com.sun.tools.javac.util.List<JCTree.JCCompilationUnit> list = comp.parseFiles(otherFiles);

		return tree;
	}
	
	private void resetView(JCCompilationUnit root) {
		fViewer.setInput(root);
//		fViewer.getTree().setEnabled(root != null);
//		fSash.setMaximizedControl(fViewer.getTree());
//		setASTUptoDate(root != null);
	}

	
	private void installModificationListener() {
		fCurrentDocument= fEditor.getDocumentProvider().getDocument(fEditor.getEditorInput());
		fCurrentDocument.addDocumentListener(fSuperListener);
	}
	
	public void handleDocumentChanged() {
		setASTUptoDate(false);
	}
	
	private void setASTUptoDate(boolean isuptoDate) {
		fRefreshAction.setEnabled(!isuptoDate && is != null);
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
	public void dispose() {
		System.out.println("dispose()");
		if (fSuperListener != null) {
			if (fEditor != null) {
				uninstallModificationListener();
			}
			ISelectionService service= getSite().getWorkbenchWindow().getSelectionService();
			service.removePostSelectionListener(fSuperListener);
			getSite().getPage().removePartListener(fSuperListener);
			FileBuffers.getTextFileBufferManager().removeFileBufferListener(fSuperListener);
			fSuperListener.dispose(); // removes reference to view
			fSuperListener= null;
		}
		super.dispose();
	}
	
}



