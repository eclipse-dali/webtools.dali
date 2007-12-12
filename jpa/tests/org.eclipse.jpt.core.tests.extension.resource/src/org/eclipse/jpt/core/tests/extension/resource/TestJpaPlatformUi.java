package org.eclipse.jpt.core.tests.extension.resource;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.IJpaUiFactory;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.jpt.ui.internal.java.details.IAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.structure.IJpaStructureProvider;

public class TestJpaPlatformUi implements IJpaPlatformUi
{
	public TestJpaPlatformUi() {
	// TODO Auto-generated constructor stub
	}

	public ListIterator<IAttributeMappingUiProvider> defaultJavaAttributeMappingUiProviders() {
		// TODO Auto-generated method stub
		return null;
	}

	public IJpaDetailsProvider detailsProvider(String fileContentType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<IJpaDetailsProvider> detailsProviders() {
		// TODO Auto-generated method stub
		return null;
	}

	public IJpaUiFactory getJpaUiFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListIterator<IAttributeMappingUiProvider> javaAttributeMappingUiProviders() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListIterator<ITypeMappingUiProvider> javaTypeMappingUiProviders() {
		// TODO Auto-generated method stub
		return null;
	}

	public IJpaStructureProvider structureProvider(String fileContentType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<IJpaStructureProvider> structureProviders() {
		// TODO Auto-generated method stub
		return null;
	}

	public void generateDDL(IJpaProject project, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}

	public void generateEntities(IJpaProject project, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}
}
