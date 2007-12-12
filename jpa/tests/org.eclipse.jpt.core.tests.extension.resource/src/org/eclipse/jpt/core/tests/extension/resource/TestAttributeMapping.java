package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeMapping;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;

public class TestAttributeMapping extends JavaAttributeMapping
{
	public TestAttributeMapping(Attribute attribute) {
		super(attribute);
	}
	
	@Override
	protected DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getKey() {
		return "test";
	}
}
