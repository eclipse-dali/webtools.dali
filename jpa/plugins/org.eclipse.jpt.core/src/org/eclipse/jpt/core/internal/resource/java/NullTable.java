package org.eclipse.jpt.core.internal.resource.java;


public class NullTable extends NullAbstractTable implements Table
{	
	protected NullTable(JavaResource parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return Table.ANNOTATION_NAME;
	}



}
