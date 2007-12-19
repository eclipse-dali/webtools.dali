package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.jpt.core.internal.content.java.mappings.JavaTypeMapping;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class TestTypeMapping extends JavaTypeMapping
{
	public TestTypeMapping(Type type) {
		super(type);
	}
	
	@Override
	protected DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}
