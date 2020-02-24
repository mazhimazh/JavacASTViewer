package astview;

import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

public class EditorUtility {
	private EditorUtility() {
		super();
	}

	public static IEditorPart getActiveEditor() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				return page.getActiveEditor();
			}
		}
		return null;
	}

	public static ITypeRoot getJavaInput(IEditorPart part) {
		IEditorInput editorInput = part.getEditorInput();
		if (editorInput != null) {
			IJavaElement input = JavaUI.getEditorInputJavaElement(editorInput);
			if (input instanceof ITypeRoot) {
				return (ITypeRoot) input;
			}
		}
		return null;
	}

	public static URI getURI(IEditorPart part) {
		IFile file = part.getEditorInput().getAdapter(IFile.class);
//		try {
//			System.out.println(file.getLocationURI());
//			InputStream i = file.getContents();
//			
////			System.out.println(readInfoStream(i));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		AbstractTextEditor xx = part.getEditorInput().getAdapter(AbstractTextEditor.class);
//		if (xx != null) {
//		IDocument document = xx.getDocumentProvider().getDocument(
//		            part.getEditorInput());
//		String content = document.get();
//		 System.out.println(content + "===========");
//		}
		
		return file.getLocationURI();
	}

	public static void selectInEditor(ITextEditor editor, int offset, int length) {
		IEditorPart active = getActiveEditor();
		if (active != editor) {
			editor.getSite().getPage().activate(editor);
		}
		editor.selectAndReveal(offset, length);
	}

//	private static final String DEFAULT_ENCODING = "GBK";// 编码
//	private static final int PROTECTED_LENGTH = 51200;// 输入流保护 50KB
//
//	public static String readInfoStream(InputStream input) throws Exception {
//		if (input == null) {
//			throw new Exception("输入流为null");
//		}
//		// 字节数组
//		byte[] bcache = new byte[2048];
//		int readSize = 0;// 每次读取的字节长度
//		int totalSize = 0;// 总字节长度
//		ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
//		try {
//			// 一次性读取2048字节
//			while ((readSize = input.read(bcache)) > 0) {
//				totalSize += readSize;
//				if (totalSize > PROTECTED_LENGTH) {
//					throw new Exception("输入流超出50K大小限制");
//				}
//				// 将bcache中读取的input数据写入infoStream
//				infoStream.write(bcache, 0, readSize);
//			}
//		} catch (IOException e1) {
//			throw new Exception("输入流读取异常");
//		} finally {
//			try {
//				// 输入流关闭
//				input.close();
//			} catch (IOException e) {
//				throw new Exception("输入流关闭异常");
//			}
//		}
//
//		try {
//			return infoStream.toString(DEFAULT_ENCODING);
//		} catch (UnsupportedEncodingException e) {
//			throw new Exception("输出异常");
//		}
//	}
}
