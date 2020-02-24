package astview;

import java.io.File;
import java.net.URI;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.Context;

import astview.listener.ListenerMix;
import astview.provider.ASTViewSelectionProvider;
import astview.provider.ViewContentProvider;
import astview.provider.ViewLabelProvider;


public class JavacASTViewer extends ViewPart {
	
	public static final String ID = "javacastviewer";
	
	private TreeViewer fViewer;
	private SashForm fSash;
	
	private ListenerMix fSuperListener;

	
	private Action fRefreshAction;
	
	private IDocument fCurrentDocument;
	private ITextEditor fEditor;
	
	URI is;

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.setSite(site);
		if (fSuperListener == null) {
			fSuperListener= new ListenerMix(this);

			// 下面是什么意思？？
			ISelectionService service= site.getWorkbenchWindow().getSelectionService();
			service.addPostSelectionListener(fSuperListener);
			site.getPage().addPartListener(fSuperListener);
			FileBuffers.getTextFileBufferManager().addFileBufferListener(fSuperListener);
		}
	}

	public void createPartControl(Composite parent) {
		fSash= new SashForm(parent, SWT.VERTICAL | SWT.SMOOTH);
		fViewer = new TreeViewer(fSash, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		
		fViewer.setLabelProvider(new ViewLabelProvider());
		fViewer.setContentProvider(new ViewContentProvider());
		fViewer.addSelectionChangedListener(fSuperListener);
		fViewer.addDoubleClickListener(fSuperListener);
		
		makeActions();
		contributeToActionBars();
		getSite().setSelectionProvider(new ASTViewSelectionProvider(fViewer));
		
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
		
	}
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		bars.getToolBarManager().add(fRefreshAction);
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
			installModificationListener();
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
	
	
	private JCCompilationUnit createAST(URI is) {
		
		Context context = new Context();
		JavacFileManager.preRegister(context);
		JavaFileManager fileManager = context.get(JavaFileManager.class);
		JavaCompiler comp = JavaCompiler.instance(context);

		JavacFileManager dfm = (JavacFileManager)fileManager;
		com.sun.tools.javac.util.List<JavaFileObject> otherFiles = com.sun.tools.javac.util.List.nil();
		for (JavaFileObject fo : dfm.getJavaFileObjects(new File(is)))
			otherFiles = otherFiles.prepend(fo);

		com.sun.tools.javac.util.List<JCTree.JCCompilationUnit> list =  comp.parseFiles(otherFiles);

		return list.get(0);
	}
	
	private void resetView(JCCompilationUnit root) {
		fViewer.setInput(root);
		fViewer.getTree().setEnabled(root != null);
		fSash.setMaximizedControl(fViewer.getTree());
		setASTUptoDate(root != null);
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
	
}



