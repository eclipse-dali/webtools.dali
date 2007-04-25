package org.eclipse.jpt.ui.internal.java.structure;

import java.util.Collection;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jpt.core.internal.content.java.JpaJavaPackage;

public class JavaCompilationUnitItemProvider extends ItemProviderAdapter
	implements IEditingDomainItemProvider, 
		IStructuredItemContentProvider,
		ITreeItemContentProvider, 
		IItemLabelProvider
{
	public JavaCompilationUnitItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	@Override
	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(JpaJavaPackage.Literals.JPA_COMPILATION_UNIT__TYPES);
		}
		return childrenFeatures;
	}
}
