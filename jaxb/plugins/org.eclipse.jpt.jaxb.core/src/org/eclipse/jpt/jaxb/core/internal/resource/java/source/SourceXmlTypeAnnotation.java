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
import org.eclipse.jpt.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AbstractType;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.AbstractJavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;

/**
 * javax.xml.bind.annotation.XmlType
 */
public final class SourceXmlTypeAnnotation
	extends SourceAnnotation<AbstractType>
	implements XmlTypeAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> FACTORY_CLASS_ADAPTER = buildFactoryClassAdapter();
	private final AnnotationElementAdapter<String> factoryClassAdapter;
	private String factoryClass;
	
	private String fullyQualifiedFactoryClassName;

	private static final DeclarationAnnotationElementAdapter<String> FACTORY_METHOD_ADAPTER = buildFactoryMethodAdapter();
	private final AnnotationElementAdapter<String> factoryMethodAdapter;
	private String factoryMethod;

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;

	private static final DeclarationAnnotationElementAdapter<String> NAMESPACE_ADAPTER = buildNamespaceAdapter();
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;

	private final DeclarationAnnotationElementAdapter<String[]> propOrderDeclarationAdapter;
	private final AnnotationElementAdapter<String[]> propOrderAdapter;
	private final Vector<String> propOrder = new Vector<String>();

	public SourceXmlTypeAnnotation(AbstractJavaResourceType parent, AbstractType type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
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

	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return buildArrayAnnotationElementAdapter(annotationAdapter, elementName, AnnotationStringArrayExpressionConverter.forStrings());
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String[]> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String[]>(annotationAdapter, elementName, false, converter);
	}

	private AnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String[]> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String[]>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.factoryClass = this.buildFactoryClass(astRoot);
		this.fullyQualifiedFactoryClassName = this.buildFullyQualifiedFactoryClassName(astRoot);
		this.factoryMethod = this.buildFactoryMethod(astRoot);
		this.name = this.buildName(astRoot);
		this.namespace = this.buildNamespace(astRoot);
		this.initializePropOrder(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncFactoryClass(this.buildFactoryClass(astRoot));
		this.syncFullyQualifiedFactoryClassName(this.buildFullyQualifiedFactoryClassName(astRoot));
		this.syncFactoryMethod(this.buildFactoryMethod(astRoot));
		this.syncName(this.buildName(astRoot));
		this.syncNamespace(this.buildNamespace(astRoot));
		this.syncPropOrder(astRoot);
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

	private String buildFactoryClass(CompilationUnit astRoot) {
		return this.factoryClassAdapter.getValue(astRoot);
	}

	public TextRange getFactoryClassTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(FACTORY_CLASS_ADAPTER, astRoot);
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

	private String buildFullyQualifiedFactoryClassName(CompilationUnit astRoot) {
		return (this.factoryClass == null) ? null : ASTTools.resolveFullyQualifiedName(this.factoryClassAdapter.getExpression(astRoot));
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

	private String buildFactoryMethod(CompilationUnit astRoot) {
		return this.factoryMethodAdapter.getValue(astRoot);
	}

	public TextRange getFactoryMethodTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(FACTORY_METHOD_ADAPTER, astRoot);
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

	private String buildName(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(NAME_ADAPTER, astRoot);
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

	private String buildNamespace(CompilationUnit astRoot) {
		return this.namespaceAdapter.getValue(astRoot);
	}

	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(NAMESPACE_ADAPTER, astRoot);
	}

	// ***** prop order
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
		CollectionTools.move(this.propOrder, targetIndex, sourceIndex);
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

	private void initializePropOrder(CompilationUnit astRoot) {
		String[] astPropOrder = this.propOrderAdapter.getValue(astRoot);
		for (int i = 0; i < astPropOrder.length; i++) {
			this.propOrder.add(astPropOrder[i]);
		}
	}

	private void syncPropOrder(CompilationUnit astRoot) {
		String[] astPropOrder = this.propOrderAdapter.getValue(astRoot);
		this.synchronizeList(Arrays.asList(astPropOrder), this.propOrder, PROP_ORDER_LIST);
	}


	//*********** static methods ****************

	private static DeclarationAnnotationElementAdapter<String> buildFactoryClassAdapter() {
		return buildAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_TYPE__FACTORY_CLASS, SimpleTypeStringExpressionConverter.instance());
	}

	static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, false, converter);
	}

	private static DeclarationAnnotationElementAdapter<String> buildFactoryMethodAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_TYPE__FACTORY_METHOD, false); // false = do not remove annotation when empty
	}

	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_TYPE__NAME, false); // false = do not remove annotation when empty
	}

	private static DeclarationAnnotationElementAdapter<String> buildNamespaceAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_TYPE__NAMESPACE, false); // false = do not remove annotation when empty
	}

}
