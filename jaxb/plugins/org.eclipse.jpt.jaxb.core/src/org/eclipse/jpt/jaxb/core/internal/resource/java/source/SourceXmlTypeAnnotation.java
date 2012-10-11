/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.IndexedConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedExpressionConverter;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;

/**
 * javax.xml.bind.annotation.XmlType
 */
public final class SourceXmlTypeAnnotation
	extends SourceAnnotation
	implements XmlTypeAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_TYPE);

	private static final DeclarationAnnotationElementAdapter<String> FACTORY_CLASS_ADAPTER = buildFactoryClassAdapter();
	private final AnnotationElementAdapter<String> factoryClassAdapter;
	private String factoryClass;
	private TextRange factoryClassTextRange;
	
	private String fullyQualifiedFactoryClassName;

	private static final DeclarationAnnotationElementAdapter<String> FACTORY_METHOD_ADAPTER = buildFactoryMethodAdapter();
	private final AnnotationElementAdapter<String> factoryMethodAdapter;
	private String factoryMethod;
	private TextRange factoryMethodTextRange;

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;
	private TextRange nameTextRange;

	private static final DeclarationAnnotationElementAdapter<String> NAMESPACE_ADAPTER = buildNamespaceAdapter();
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;
	private TextRange namespaceTextRange;

	private final IndexedDeclarationAnnotationElementAdapter<String> propOrderDeclarationAdapter;
	private final AnnotationElementAdapter<String[]> propOrderAdapter;
	private final Vector<String> propOrder = new Vector<String>();
	private TextRange propOrderTextRange;
	private final Vector<TextRange> propTextRanges = new Vector<TextRange>();

	public SourceXmlTypeAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		super(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
		this.factoryClassAdapter = this.buildAnnotationElementAdapter(FACTORY_CLASS_ADAPTER);
		this.factoryMethodAdapter = this.buildAnnotationElementAdapter(FACTORY_METHOD_ADAPTER);
		this.nameAdapter = this.buildAnnotationElementAdapter(NAME_ADAPTER);
		this.namespaceAdapter = this.buildAnnotationElementAdapter(NAMESPACE_ADAPTER);
		this.propOrderDeclarationAdapter = buildArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_TYPE__PROP_ORDER);
		this.propOrderAdapter = this.buildArrayAnnotationElementAdapter(this.propOrderDeclarationAdapter);
	}

	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	private static IndexedDeclarationAnnotationElementAdapter<String> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return buildArrayAnnotationElementAdapter(annotationAdapter, elementName, AnnotationStringArrayExpressionConverter.forStrings());
	}

	private static IndexedDeclarationAnnotationElementAdapter<String> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, IndexedExpressionConverter<String> converter) {
		return new IndexedConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, converter);
	}

	private AnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String[]> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String[]>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return JAXB.XML_TYPE;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.factoryClass = this.buildFactoryClass(astAnnotation);
		this.factoryClassTextRange = this.buildFactoryClassTextRange(astAnnotation);
		this.fullyQualifiedFactoryClassName = this.buildFullyQualifiedFactoryClassName(astAnnotation);
		this.factoryMethod = this.buildFactoryMethod(astAnnotation);
		this.factoryMethodTextRange = this.buildFactoryMethodTextRange(astAnnotation);
		this.name = this.buildName(astAnnotation);
		this.nameTextRange = this.buildNameTextRange(astAnnotation);
		this.namespace = this.buildNamespace(astAnnotation);
		this.namespaceTextRange = this.buildNamespaceTextRange(astAnnotation);
		this.initializePropOrder(astAnnotation);
		this.propOrderTextRange = this.buildPropOrderTextRange(astAnnotation);
		this.syncPropTextRanges(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncFactoryClass(this.buildFactoryClass(astAnnotation));
		this.factoryClassTextRange = this.buildFactoryClassTextRange(astAnnotation);
		this.syncFullyQualifiedFactoryClassName(this.buildFullyQualifiedFactoryClassName(astAnnotation));
		this.syncFactoryMethod(this.buildFactoryMethod(astAnnotation));
		this.factoryMethodTextRange = this.buildFactoryMethodTextRange(astAnnotation);
		this.syncName(this.buildName(astAnnotation));
		this.nameTextRange = this.buildNameTextRange(astAnnotation);
		this.syncNamespace(this.buildNamespace(astAnnotation));
		this.namespaceTextRange = this.buildNamespaceTextRange(astAnnotation);
		this.syncPropOrder(astAnnotation);
		this.propOrderTextRange = this.buildPropOrderTextRange(astAnnotation);
		this.syncPropTextRanges(astAnnotation);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** XmlTypeAnnotation implementation **********

	// ***** factoryClass
	public String getFactoryClass() {
		return this.factoryClass;
	}

	public void setFactoryClass(String factoryClass) {
		if (this.attributeValueHasChanged(this.factoryClass, factoryClass)) {
			this.factoryClass = factoryClass;
			this.factoryClassAdapter.setValue(factoryClass);
		}
	}

	private void syncFactoryClass(String astFactoryClass) {
		String old = this.factoryClass;
		this.factoryClass = astFactoryClass;
		this.firePropertyChanged(FACTORY_CLASS_PROPERTY, old, astFactoryClass);
	}

	private String buildFactoryClass(Annotation astAnnotation) {
		return this.factoryClassAdapter.getValue(astAnnotation);
	}

	public TextRange getFactoryClassTextRange() {
		return this.factoryClassTextRange;
	}

	private TextRange buildFactoryClassTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(FACTORY_CLASS_ADAPTER, astAnnotation);
	}

	
	// ***** fully-qualified factory class name
	public String getFullyQualifiedFactoryClassName() {
		return this.fullyQualifiedFactoryClassName;
	}

	private void syncFullyQualifiedFactoryClassName(String name) {
		String old = this.fullyQualifiedFactoryClassName;
		this.fullyQualifiedFactoryClassName = name;
		this.firePropertyChanged(FULLY_QUALIFIED_FACTORY_CLASS_NAME_PROPERTY, old, name);
	}

	private String buildFullyQualifiedFactoryClassName(Annotation astAnnotation) {
		return (this.factoryClass == null) ? null : ASTTools.resolveFullyQualifiedName(this.factoryClassAdapter.getExpression(astAnnotation));
	}

	// ***** factoryMethod
	public String getFactoryMethod() {
		return this.factoryMethod;
	}

	public void setFactoryMethod(String factoryMethod) {
		if (this.attributeValueHasChanged(this.factoryMethod, factoryMethod)) {
			this.factoryMethod = factoryMethod;
			this.factoryMethodAdapter.setValue(factoryMethod);
		}
	}

	private void syncFactoryMethod(String astFactoryMethod) {
		String old = this.factoryMethod;
		this.factoryMethod = astFactoryMethod;
		this.firePropertyChanged(FACTORY_METHOD_PROPERTY, old, astFactoryMethod);
	}

	private String buildFactoryMethod(Annotation astAnnotation) {
		return this.factoryMethodAdapter.getValue(astAnnotation);
	}

	public TextRange getFactoryMethodTextRange() {
		return this.factoryMethodTextRange;
	}

	private TextRange buildFactoryMethodTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(FACTORY_METHOD_ADAPTER, astAnnotation);
	}

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.attributeValueHasChanged(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(Annotation astAnnotation) {
		return this.nameAdapter.getValue(astAnnotation);
	}

	private TextRange buildNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(NAME_ADAPTER, astAnnotation);
	}

	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}
	
	public boolean nameTouches(int pos) {
		return this.textRangeTouches(this.nameTextRange, pos);
	}
	
	
	// ***** namespace
	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		if (this.attributeValueHasChanged(this.namespace, namespace)) {
			this.namespace = namespace;
			this.namespaceAdapter.setValue(namespace);
		}
	}

	private void syncNamespace(String astNamespace) {
		String old = this.namespace;
		this.namespace = astNamespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, astNamespace);
	}

	private String buildNamespace(Annotation astAnnotation) {
		return this.namespaceAdapter.getValue(astAnnotation);
	}

	private TextRange buildNamespaceTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(NAMESPACE_ADAPTER, astAnnotation);
	}

	public TextRange getNamespaceTextRange() {
		return this.namespaceTextRange;
	}
	
	public boolean namespaceTouches(int pos) {
		return this.textRangeTouches(this.namespaceTextRange, pos);
	}
	
	
	// ***** prop order *****
	
	public ListIterable<String> getPropOrder() {
		return new LiveCloneListIterable<String>(this.propOrder);
	}
	
	public int getPropOrderSize() {
		return this.propOrder.size();
	}
	
	public void addProp(String prop) {
		this.addProp(this.propOrder.size(), prop);
	}
	
	public void addProp(int index, String prop) {
		this.propOrder.add(index, prop);
		this.writePropOrder();
	}

	public void moveProp(int targetIndex, int sourceIndex) {
		ListTools.move(this.propOrder, targetIndex, sourceIndex);
		this.writePropOrder();
	}

	public void removeProp(String prop) {
		this.propOrder.remove(prop);
		this.writePropOrder();
	}

	public void removeProp(int index) {
		this.propOrder.remove(index);
		this.writePropOrder();
	}

	private void writePropOrder() {
		this.propOrderAdapter.setValue(this.propOrder.toArray(new String[this.propOrder.size()]));
	}

	private void initializePropOrder(Annotation astAnnotation) {
		String[] astPropOrder = this.propOrderAdapter.getValue(astAnnotation);
		for (int i = 0; i < astPropOrder.length; i++) {
			this.propOrder.add(astPropOrder[i]);
		}
	}

	private void syncPropOrder(Annotation astAnnotation) {
		String[] astPropOrder = this.propOrderAdapter.getValue(astAnnotation);
		this.synchronizeList(Arrays.asList(astPropOrder), this.propOrder, PROP_ORDER_LIST);
	}


	public TextRange getPropOrderTextRange() {
		return this.propOrderTextRange;
	}

	private TextRange buildPropOrderTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.propOrderDeclarationAdapter, astAnnotation);
	}
	
	public boolean propOrderTouches(int pos) {
		return this.textRangeTouches(this.propOrderTextRange, pos);
	}
	
	private void syncPropTextRanges(Annotation astAnnotation) {
		this.propTextRanges.clear();
		for (int i = 0; i < this.propOrder.size(); i++) {
			TextRange propTextRange = this.getElementTextRange(this.propOrderDeclarationAdapter, i, astAnnotation);
			this.propTextRanges.add(i, propTextRange);
		}
	}
	
	public TextRange getPropTextRange(int index) {
		return this.propTextRanges.get(index);
	}
	
	public boolean propTouches(int index, int pos) {
		return this.textRangeTouches(this.getPropTextRange(index), pos);
	}
	
	
	//*********** static methods ****************

	private static DeclarationAnnotationElementAdapter<String> buildFactoryClassAdapter() {
		return buildAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_TYPE__FACTORY_CLASS, SimpleTypeStringExpressionConverter.instance());
	}

	static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, converter);
	}

	private static DeclarationAnnotationElementAdapter<String> buildFactoryMethodAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_TYPE__FACTORY_METHOD);
	}

	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_TYPE__NAME);
	}

	private static DeclarationAnnotationElementAdapter<String> buildNamespaceAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_TYPE__NAMESPACE);
	}
}
