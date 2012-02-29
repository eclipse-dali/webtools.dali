package org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.binary.BinaryXmlDiscriminatorValueAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.source.SourceXmlDiscriminatorValueAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;


public class XmlDiscriminatorValueAnnotationDefinition
		implements AnnotationDefinition {
	
	// singleton
	private static final AnnotationDefinition INSTANCE = new XmlDiscriminatorValueAnnotationDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private XmlDiscriminatorValueAnnotationDefinition() {
		super();
	}
	
	
	public String getAnnotationName() {
		return ELJaxb.XML_DISCRIMINATOR_VALUE;
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceXmlDiscriminatorValueAnnotation(parent, annotatedElement);
	}
	
	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryXmlDiscriminatorValueAnnotation(parent, jdtAnnotation);
	}
}
