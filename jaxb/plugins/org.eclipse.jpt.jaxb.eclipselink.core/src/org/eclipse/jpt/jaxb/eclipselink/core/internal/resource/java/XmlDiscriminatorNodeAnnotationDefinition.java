package org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.binary.BinaryXmlDiscriminatorNodeAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.source.SourceXmlDiscriminatorNodeAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;


public class XmlDiscriminatorNodeAnnotationDefinition
		implements AnnotationDefinition {
	
	// singleton
	private static final AnnotationDefinition INSTANCE = new XmlDiscriminatorNodeAnnotationDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private XmlDiscriminatorNodeAnnotationDefinition() {
		super();
	}
	
	
	public String getAnnotationName() {
		return ELJaxb.XML_DISCRIMINATOR_NODE;
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceXmlDiscriminatorNodeAnnotation(parent, annotatedElement);
	}
	
	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}
	
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryXmlDiscriminatorNodeAnnotation(parent, jdtAnnotation);
	}
}
