/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import java.util.Arrays;
import java.util.Vector;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;

/**
 * javax.xml.bind.annotation.XmlSeeAlso
 */
public final class SourceXmlSeeAlsoAnnotation
		extends SourceAnnotation
		implements XmlSeeAlsoAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_SEE_ALSO);
	
	private final DeclarationAnnotationElementAdapter<String[]> valueDeclarationAdapter;
	
	private final AnnotationElementAdapter<String[]> valueAdapter;
	
	private final Vector<String> classes;
	
	/*
	 * We want this event fired when the fq classes change by themselves, not as a result
	 * of the value changing.
	 */
	private boolean suppressFQClassesEventNotification = false;
	
	private final Vector<String> fullyQualifiedClasses;
	
	
	public SourceXmlSeeAlsoAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		super(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
		this.valueDeclarationAdapter = buildArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_SEE_ALSO__VALUE);
		this.valueAdapter = this.buildArrayAnnotationElementAdapter(this.valueDeclarationAdapter);
		this.classes = new Vector<String>();
		this.fullyQualifiedClasses = new Vector<String>();
	}
	
	
	private AnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String[]> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String[]>(this.annotatedElement, daea);
	}
	
	public String getAnnotationName() {
		return JAXB.XML_SEE_ALSO;
	}
	
	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		for (String astClass : this.valueAdapter.getValue(astAnnotation)) {
			this.classes.add(astClass);
		}
		CollectionTools.addAll(this.fullyQualifiedClasses, buildFullyQualifiedClasses(astAnnotation));
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		String[] astClasses = this.valueAdapter.getValue(astAnnotation);
		synchronizeList(Arrays.asList(astClasses), this.classes, CLASSES_LIST);
		
		if (this.suppressFQClassesEventNotification) {
			this.fullyQualifiedClasses.clear();
			CollectionTools.addAll(this.fullyQualifiedClasses, buildFullyQualifiedClasses(astAnnotation));
			this.suppressFQClassesEventNotification = false;
		}
		else {
			synchronizeList(buildFullyQualifiedClasses(astAnnotation), this.fullyQualifiedClasses, FULLY_QUALIFIED_CLASSES_LIST);
		}
	}
	
	protected Iterable<String> buildFullyQualifiedClasses(final Annotation astAnnotation) {
		Expression expression = this.valueAdapter.getExpression(astAnnotation);
		if (expression == null) {
			return EmptyIterable.<String>instance();
		}
		if (expression.getNodeType() == ASTNode.TYPE_LITERAL) {
			return new SingleElementIterable<String>(ASTTools.resolveFullyQualifiedName(expression));
		}
		return ASTTools.resolveFullyQualifiedNames(expression);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.classes);
	}
	
	
	// ********** XmlSeeAlsoAnnotation implementation **********
	
	// ***** classes
	public ListIterable<String> getClasses() {
		return IterableTools.cloneLive(this.classes);
	}
	
	public int getClassesSize() {
		return this.classes.size();
	}
	
	public void addClass(String clazz) {
		this.addClass(this.classes.size(), clazz);
	}
	
	public void addClass(int index, String clazz) {
		this.classes.add(index, clazz);
		this.suppressFQClassesEventNotification = true;
		this.writeClasses();
	}
	
	public void moveClass(int targetIndex, int sourceIndex) {
		ListTools.move(this.classes, targetIndex, sourceIndex);
		this.suppressFQClassesEventNotification = true;
		this.writeClasses();
	}
	
	public void removeClass(int index) {
		this.classes.remove(index);
		this.suppressFQClassesEventNotification = true;
		this.writeClasses();
	}
	
	public ListIterable<String> getFullyQualifiedClasses() {
		return IterableTools.cloneLive(this.fullyQualifiedClasses);
	}
	
	private void writeClasses() {
		this.valueAdapter.setValue(this.classes.toArray(new String[this.classes.size()]));
	}
	
	
	//*********** static methods ****************
	
	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return buildArrayAnnotationElementAdapter(annotationAdapter, elementName, AnnotationStringArrayExpressionConverter.forTypes());
	}
	
	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String[]> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String[]>(annotationAdapter, elementName, converter);
	}
}
