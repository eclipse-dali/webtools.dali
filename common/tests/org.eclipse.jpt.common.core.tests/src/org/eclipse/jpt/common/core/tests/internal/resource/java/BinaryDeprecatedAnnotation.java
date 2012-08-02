package org.eclipse.jpt.common.core.tests.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;


public class BinaryDeprecatedAnnotation
		extends BinaryAnnotation {
	
	public BinaryDeprecatedAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return Deprecated.class.getName();
	}
}
