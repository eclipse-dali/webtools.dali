package org.eclipse.jpt.core.internal.resource.jar;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaResourceModelListener;
import org.eclipse.jpt.core.resource.jar.JarResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * JAR package fragment root
 */
public class JarResourcePackageFragmentRootImpl
	extends AbstractJarResourceNode
	implements JarResourcePackageFragmentRoot
{
	/** JDT package fragment root */
	private final IPackageFragmentRoot packageFragmentRoot;

	/** pluggable annotation provider */
	private final JpaAnnotationProvider annotationProvider;

	/** listeners notified whenever the resource model changes */
	private final ListenerList<JpaResourceModelListener> resourceModelListenerList;

	/** package fragments in the JAR */
//	private final ArrayList<JarResourcePackageFragment> packageFragments;


	// ********** construction **********

	public JarResourcePackageFragmentRootImpl(IPackageFragmentRoot packageFragmentRoot, JpaAnnotationProvider annotationProvider) {
		super(null);  // the package fragment root is the root of its sub-tree
		this.packageFragmentRoot = packageFragmentRoot;
		this.annotationProvider = annotationProvider;
		this.resourceModelListenerList = new ListenerList<JpaResourceModelListener>(JpaResourceModelListener.class);
//		this.packageFragments = this.buildPackageFragments();
	}

//	protected ArrayList<JarResourcePackageFragment> buildPackageFragments() {
//		ArrayList<JarResourcePackageFragment> result = new ArrayList<JarResourcePackageFragment>();
//		for (IJavaElement pf : this.packageFragmentRoot.getChildren()) {
//			result.add(new JarResourcePackageFragmentImpl(pf));
//		}
//		return result;
//	}


	// ********** AbstractJarResourceNode overrides **********

	@Override
	protected boolean requiresParent() {
		return false;
	}

	@Override
	public JarResourcePackageFragmentRoot getJarResourcePackageFragmentRoot() {
		return this;
	}

	@Override
	public IFile getFile() {
		return (IFile) this.packageFragmentRoot.getResource();
	}

	@Override
	public JpaAnnotationProvider getAnnotationProvider() {
		return this.annotationProvider;
	}


	// ********** JarResourceNode implementation **********

	public void update() {
		//
	}


	// ********** JarResourcePackageFragmentRoot implementation **********

	public IPackageFragmentRoot getPackageFragmentRoot() {
		return this.packageFragmentRoot;
	}

	public Iterator<JavaResourcePersistentType> persistableTypes() {
		return EmptyIterator.<JavaResourcePersistentType>instance();
	}

	public void resourceModelChanged() {
		for (JpaResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			listener.resourceModelChanged();
		}
	}


	// ********** JpaResourceModel implementation **********

	public void addResourceModelListener(JpaResourceModelListener listener) {
		this.resourceModelListenerList.add(listener);
	}

	public void removeResourceModelListener(JpaResourceModelListener listener) {
		this.resourceModelListenerList.remove(listener);
	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getFile().getName());
	}

}
