package org.eclipse.jpt.core.tests.extension.resource;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.internal.structure.JpaStructureProvider;
import org.eclipse.jpt.ui.java.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.java.details.TypeMappingUiProvider;

public class TestJpaPlatformUi implements JpaPlatformUi
{
	public TestJpaPlatformUi() {
	// TODO Auto-generated constructor stub
	}

	public ListIterator<AttributeMappingUiProvider> defaultJavaAttributeMappingUiProviders() {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaDetailsProvider detailsProvider(String fileContentType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<JpaDetailsProvider> detailsProviders() {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaUiFactory getJpaUiFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListIterator<AttributeMappingUiProvider> javaAttributeMappingUiProviders() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListIterator<TypeMappingUiProvider> javaTypeMappingUiProviders() {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaStructureProvider structureProvider(String fileContentType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<JpaStructureProvider> structureProviders() {
		// TODO Auto-generated method stub
		return null;
	}

	public void generateDDL(JpaProject project, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}

	public void generateEntities(JpaProject project, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}
}
