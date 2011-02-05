/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AbstractType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.AbstractJavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;

/**
 * javax.xml.bind.annotation.XmlSeeAlso
 */
public final class SourceXmlSeeAlsoAnnotation
	extends SourceAnnotation<AbstractType>
	implements XmlSeeAlsoAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final DeclarationAnnotationElementAdapter<String[]> valueDeclarationAdapter;
	private final AnnotationElementAdapter<String[]> valueAdapter;
	private final Vector<String> classes = new Vector<String>();

	public SourceXmlSeeAlsoAnnotation(AbstractJavaResourceType parent, AbstractType type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.valueDeclarationAdapter = buildArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_SEE_ALSO__VALUE);
		this.valueAdapter = this.buildArrayAnnotationElementAdapter(this.valueDeclarationAdapter);
	}

	private AnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String[]> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String[]>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.initializeClasses(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncClasses(astRoot);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.classes);
	}


	// ********** XmlSeeAlsoAnnotation implementation **********

	// ***** classes
	public ListIterable<String> getClasses() {
		return new LiveCloneListIterable<String>(this.classes);
	}

	public int getClassesSize() {
		return this.classes.size();
	}

	public void addClass(String clazz) {
		this.addClass(this.classes.size(), clazz);
	}

	public void addClass(int index, String clazz) {
		this.classes.add(index, clazz);
		this.writeClasses();
	}

	public void moveClass(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.classes, targetIndex, sourceIndex);
		this.writeClasses();
	}

	public void removeClass(String prop) {
		this.classes.remove(prop);
		this.writeClasses();
	}

	public void removeClass(int index) {
		this.classes.remove(index);
		this.writeClasses();
	}

	private void writeClasses() {
		this.valueAdapter.setValue(this.classes.toArray(new String[this.classes.size()]));
	}

	private void initializeClasses(CompilationUnit astRoot) {
		String[] astClasses = this.valueAdapter.getValue(astRoot);
		for (int i = 0; i < astClasses.length; i++) {
			this.classes.add(astClasses[i]);
		}
	}

	private void syncClasses(CompilationUnit astRoot) {
		String[] astClasses = this.valueAdapter.getValue(astRoot);
		this.synchronizeList(Arrays.asList(astClasses), this.classes, CLASSES_LIST);
	}


	//*********** static methods ****************


	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return buildArrayAnnotationElementAdapter(annotationAdapter, elementName, AnnotationStringArrayExpressionConverter.forTypes());
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String[]> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String[]>(annotationAdapter, elementName, converter);
	}


}
