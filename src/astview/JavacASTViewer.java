package astview;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;

import astview.listener.ListenerMix;
import astview.provider.ASTViewSelectionProvider;
import astview.provider.TrayContentProvider;
import astview.provider.TrayLabelProvider;
import astview.provider.ViewContentProvider;
import astview.provider.ViewLabelProvider;


public class JavacASTViewer extends ViewPart {
	
	public static final String ID = "javacastviewer";
	
	private TreeViewer fViewer;
	private DrillDownAdapter fDrillDownAdapter;
	private SashForm fSash;
	
	private ListenerMix fSuperListener;
	private ISelectionChangedListener fTrayUpdater;
	
	private TreeViewer fTray;
	private ArrayList<Object> fTrayRoots;
	
	private Action fRefreshAction;
	
	private IDocument fCurrentDocument;
	private ITextEditor fEditor;
	
	URI is;

	private int String;
	
	
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
//		fViewer.setInput(getSite());
		fViewer.addSelectionChangedListener(fSuperListener);
		fViewer.addDoubleClickListener(fSuperListener);
		
		fDrillDownAdapter = new DrillDownAdapter(fViewer);
		
		ViewForm trayForm= new ViewForm(fSash, SWT.NONE);
		Label label= new Label(trayForm, SWT.NONE);
		label.setText(" Comparison Tray (* = selection in the upper tree):"); //$NON-NLS-1$
		trayForm.setTopLeft(label);

		fTray= new TreeViewer(trayForm, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		trayForm.setContent(fTray.getTree());
		
		
		fTray.setContentProvider(new TrayContentProvider());
		fTray.setLabelProvider(new TrayLabelProvider());
		fTray.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
		fTrayUpdater= new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
//				IStructuredSelection viewerSelection= (IStructuredSelection) fViewer.getSelection();
//				if (viewerSelection.size() == 1) {
//					Object first= viewerSelection.getFirstElement();
//					if (unwrapAttribute(first) != null) {
//						trayLabelProvider.setViewerElement(first);
//						return;
//					}
//				}
//				trayLabelProvider.setViewerElement(null);
				System.out.println("fTrayUpdater->selectionChanged(SelectionChangedEvent event)");
			}
		};
		fTray.addPostSelectionChangedListener(fTrayUpdater);
		fViewer.addPostSelectionChangedListener(fTrayUpdater);
		fTray.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
//				performTrayDoubleClick();
				System.out.println("fTray->doubleClick()");
			}
		});
		fTray.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
//				IStructuredSelection selection= (IStructuredSelection) event.getSelection();
//				fDeleteAction.setEnabled(selection.size() >= 1 && fTray.getTree().isFocusControl());
				System.out.println("fTray->addSelectionChangedListener()");
			}
		});
		fTray.getTree().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
//				IStructuredSelection selection= (IStructuredSelection) fTray.getSelection();
//				fDeleteAction.setEnabled(selection.size() >= 1);
				System.out.println("fTray->FocusAdapter()");
			}
			@Override
			public void focusLost(FocusEvent e) {
//				fDeleteAction.setEnabled(false);
				System.out.println("fTray->focusLost()");
			}
		});
		
		makeActions();
		hookContextMenu();
		hookTrayContextMenu();
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
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				JavacASTViewer.this.fillContextMenu(manager);
//				System.out.println("hookContextMenu->menuAboutToShow(IMenuManager manager)");
			}
		});
		Menu menu = menuMgr.createContextMenu(fViewer.getControl());
		fViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, fViewer);
	}
	
	private void hookTrayContextMenu() {
		MenuManager menuMgr = new MenuManager("#TrayPopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
//				manager.add(fCopyAction);
//				manager.add(fDeleteAction);
//				manager.add(new Separator());
//				manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
				System.out.println("hookTrayContextMenu->menuAboutToShow(IMenuManager manager)");
			}
		});
		Menu menu = menuMgr.createContextMenu(fTray.getControl());
		fTray.getControl().setMenu(menu);
		getSite().registerContextMenu("#TrayPopupMenu", menuMgr, fTray); //$NON-NLS-1$
	}
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager()); // 下拉菜单栏
		fillLocalToolBar(bars.getToolBarManager()); // 工具栏
		
//		bars.setGlobalActionHandler(ActionFactory.COPY.getId(), fCopyAction);
//		bars.setGlobalActionHandler(ActionFactory.REFRESH.getId(), fFocusAction);
//		bars.setGlobalActionHandler(ActionFactory.DELETE.getId(), fDeleteAction);
//
//		IHandlerService handlerService= getViewSite().getService(IHandlerService.class);
//		handlerService.activateHandler(IWorkbenchCommandConstants.NAVIGATE_TOGGLE_LINK_WITH_EDITOR, 
//				new ActionHandler(fLinkWithEditor));
		
	}
	
	private void fillLocalPullDown(IMenuManager manager) {
//		for (ASTView.ASTLevelToggle action : fASTVersionToggleActions) {
//			manager.add(action);
//		}
		manager.add(new Separator());
//		manager.add(fCreateBindingsAction);
//		manager.add(fStatementsRecoveryAction);
//		manager.add(fBindingsRecoveryAction);
//		manager.add(fIgnoreMethodBodiesAction);
//		manager.add(new Separator());
//		for (ASTView.ASTInputKindAction action : fASTInputKindActions) {
//			manager.add(action);
//		}
//		manager.add(new Separator());
//		manager.add(fFindDeclaringNodeAction);
//		manager.add(fParseBindingFromKeyAction);
//		manager.add(fParseBindingFromElementAction);
//		manager.add(new Separator());
//		manager.add(fFilterNonRelevantAction);
//		manager.add(fLinkWithEditor);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
//		manager.add(fFocusAction);
		manager.add(fRefreshAction);
//		manager.add(fClearAction);
		manager.add(new Separator());
		fDrillDownAdapter.addNavigationActions(manager);
		manager.add(new Separator());
//		manager.add(fExpandAction);
//		manager.add(fCollapseAction);
//		manager.add(fLinkWithEditor);
	}
	
	protected void fillContextMenu(IMenuManager manager) {
//		ISelection selection= getSite().getSelectionProvider().getSelection();
//		if (!selection.isEmpty() && ((IStructuredSelection) selection).getFirstElement() instanceof IJavaElement) {
//			MenuManager showInSubMenu= new MenuManager(getShowInMenuLabel());
//			IWorkbenchWindow workbenchWindow= getSite().getWorkbenchWindow();
//			showInSubMenu.add(ContributionItemFactory.VIEWS_SHOW_IN.create(workbenchWindow));
//			manager.add(showInSubMenu);
//			manager.add(new Separator());
//		}
//		manager.add(fFocusAction);
		manager.add(fRefreshAction);
//		manager.add(fClearAction);
//		manager.add(fCollapseAction);
//		manager.add(fExpandAction);
//		manager.add(new Separator());
//		manager.add(fCopyAction);
//		if (fAddToTrayAction.isEnabled())
//			manager.add(fAddToTrayAction);
//		manager.add(new Separator());
//
//		fDrillDownAdapter.addNavigationActions(manager);
//		// Other plug-ins can contribute there actions here
//		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
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
//				System.out.println("dddd");
			}
		};
		fRefreshAction.setText("&Refresh AST"); //$NON-NLS-1$
		fRefreshAction.setToolTipText("Refresh AST"); //$NON-NLS-1$
		fRefreshAction.setEnabled(false);
		ASTViewImages.setImageDescriptors(fRefreshAction, ASTViewImages.REFRESH);
		
	}
	
//	protected void performRefresh() {
//		if (fTypeRoot != null) {
//			try {
//				refreshAST();
//			} catch (CoreException e) {
//				showAndLogError("Could not set AST view input ", e); //$NON-NLS-1$
//			}
//		}
//	}
	
	private void refreshAST() throws CoreException {
//		ASTNode node= getASTNodeNearSelection((IStructuredSelection) fViewer.getSelection());
//		int offset= 0;
//		int length= 0;
//		if (node != null) {
//			offset= node.getStartPosition();
//			length= node.getLength();
//		}
		internalSetInput(is);
	}



	public void setInput(ITextEditor editor) throws CoreException {
		if (editor != null) {
			fEditor = editor;
			is = EditorUtility.getInputStream(editor);
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
				setContentDescription("AST could not be created."); //$NON-NLS-1$
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
//		fTrayRoots= new ArrayList<>();
//		if (fTray != null)
//			fTray.setInput(fTrayRoots);
		setASTUptoDate(root != null);
//		fClearAction.setEnabled(root != null);
//		fFindDeclaringNodeAction.setEnabled(root != null);
//		fPreviousDouble= null; // avoid leaking AST
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



