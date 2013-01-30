/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.resource.java.InheritedAttributeKey;
import org.eclipse.jpt.common.core.internal.utility.jdt.JavaResourceTypeBinding;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;

/**
 * binary type
 */
final class BinaryType
		extends BinaryAbstractType
		implements JavaResourceType {
	
	private String superclassQualifiedName;
	
	private boolean abstract_;  // 'abstract' is a reserved word
	
	private boolean hasNoArgConstructor;
	
	private boolean hasPrivateNoArgConstructor;
	
	private final Vector<JavaResourceField> fields = new Vector<JavaResourceField>();
	
	private final Vector<JavaResourceMethod> methods = new Vector<JavaResourceMethod>();
	
	private final Hashtable<InheritedAttributeKey, JavaResourceTypeBinding> inheritedFieldTypes
			= new Hashtable<InheritedAttributeKey, JavaResourceTypeBinding>();
	
	private final Hashtable<InheritedAttributeKey, JavaResourceTypeBinding> inheritedMethodTypes
			= new Hashtable<InheritedAttributeKey, JavaResourceTypeBinding>();
	
	
	// ***** construction/initialization *****
	
	BinaryType(JavaResourceNode parent, IType type) {
		this(parent, new TypeAdapter(type));
	}
	
	private BinaryType(JavaResourceNode parent, TypeAdapter adapter) {
		super(parent, adapter);
		this.superclassQualifiedName = buildSuperclassQualifiedName();
		this.abstract_ = buildAbstract();
		this.hasNoArgConstructor = buildHasNoArgConstructor();
		this.hasPrivateNoArgConstructor = buildHasPrivateNoArgConstructor();
		CollectionTools.addAll(this.fields, buildFields());
		CollectionTools.addAll(this.methods, buildMethods());
		this.inheritedFieldTypes.putAll(buildInheritedFieldTypes(adapter.getTypeBinding()));
		this.inheritedMethodTypes.putAll(buildInheritedMethodTypes(adapter.getTypeBinding()));
	}
	
	
	public AstNodeType getAstNodeType() {
		return AstNodeType.TYPE;
	}
	
	
	// ***** overrides *****
	
	@Override
	public void update() {
		super.update();
		updateSuperclassQualifiedName();
		updateAbstract();
		updateHasNoArgConstructor();
		updateHasPrivateNoArgConstructor();
		updateFields();
		updateMethods();
		updateInheritedFieldTypes();
		updateInheritedMethodTypes();
	}
	
	
	// ********** JavaResourceType implementation **********
	
	public void synchronizeWith(TypeDeclaration typeDeclaration) {
		throw new UnsupportedOperationException();		
	}
	
	public void resolveTypes(TypeDeclaration typeDeclaration) {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** superclass qualified name *****
	
	public String getSuperclassQualifiedName() {
		return this.superclassQualifiedName;
	}
	
	private String buildSuperclassQualifiedName() {
		return convertTypeSignatureToTypeName(getSuperclassTypeSignature(getElement()));
	}
	
	private String getSuperclassTypeSignature(IType type) {
		try {
			return type.getSuperclassTypeSignature();
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return null;
		}
	}
	
	protected void updateSuperclassQualifiedName() {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** abstract *****
	
	public boolean isAbstract() {
		return this.abstract_;
	}
	
	private boolean buildAbstract() {
		try {
			return Flags.isAbstract(getElement().getFlags());
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}
	
	protected void updateAbstract() {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** no-arg constructor *****
	
	public boolean hasNoArgConstructor() {
		return this.hasNoArgConstructor;
	}
	
	private boolean buildHasNoArgConstructor() {
		return this.findNoArgConstructor(getElement()) != null;
	}
	
	private IMethod findNoArgConstructor(IType type) {
		try {
			for (IMethod method : type.getMethods()) {
				if (method.isConstructor()) {
					return method;
				}
			}
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
		}
		return null;
	}
	
	protected void updateHasNoArgConstructor() {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** private no-arg constructor *****
	
	public boolean hasPrivateNoArgConstructor() {
		return this.hasPrivateNoArgConstructor;
	}
	
	private boolean buildHasPrivateNoArgConstructor() {
		IMethod method = this.findNoArgConstructor(getElement());
		try {
			return method != null && Flags.isPrivate(method.getFlags());
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}
	
	protected void updateHasPrivateNoArgConstructor() {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** public/protected no-arg constructor *****
	
	public boolean hasPublicOrProtectedNoArgConstructor() {
		Iterable<JavaResourceMethod> constructors = this.getConstructors();
		if (IterableTools.size(constructors) == 0) {
			return true;
		}
		for (JavaResourceMethod constructor : constructors) {
			if (constructor.getParametersSize() == 0) {
				return Modifier.isPublic(constructor.getModifiers())
						|| Modifier.isProtected(constructor.getModifiers());
			}
		}
		return false;
	}
	
	public boolean hasPublicNoArgConstructor() {
		Iterable<JavaResourceMethod> constructors = this.getConstructors();
		if (IterableTools.size(constructors) == 0) {
			return true;
		}
		for (JavaResourceMethod constructor : constructors) {
			if (constructor.getParametersSize() == 0) {
				return Modifier.isPublic(constructor.getModifiers());
			}
		}
		return false;
	}
	
	protected Iterable<JavaResourceMethod> getConstructors() {
		return new FilteringIterable<JavaResourceMethod>(this.getMethods()) {
			@Override
			protected boolean accept(JavaResourceMethod method) {
				return method.isConstructor();
			}
		};
	}
	
	
	// ***** fields *****
	
	public Iterable<JavaResourceField> getFields() {
		return IterableTools.cloneLive(this.fields);
	}
	
	private Iterable<JavaResourceField> buildFields() {
		return IterableTools.transform(IterableTools.iterable(this.getFields(this.getElement())), new FieldTransformer());
	}
	
	/* CU private */ class FieldTransformer
		extends TransformerAdapter<IField, JavaResourceField>
	{
		@Override
		public JavaResourceField transform(IField field) {
			return BinaryType.this.buildField(field);
		}
	}

	private IField[] getFields(IType type) {
		try {
			return type.getFields();
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return EMPTY_FIELD_ARRAY;
		}
	}
	
	private static final IField[] EMPTY_FIELD_ARRAY = new IField[0];
	
	/* CU private */ JavaResourceField buildField(IField jdtField) {
		return new BinaryField(this, jdtField);
	}
	
	protected void updateFields() {
		throw new UnsupportedOperationException();
	}
	
	public JavaResourceField getField(String name) {
		for (JavaResourceField field : getFields()) {
			if (ObjectTools.equals(field.getName(), name)) {
				return field;
			}
		}
		return null;
	}
	
	
	// ***** methods *****
	
	public Iterable<JavaResourceMethod> getMethods() {
		return IterableTools.cloneLive(this.methods);
	}
	
	private Iterable<JavaResourceMethod> buildMethods() {
		return IterableTools.transform(IterableTools.iterable(this.getMethods(this.getElement())), new MethodTransformer());
	}
	
	/* CU private */ class MethodTransformer
		extends TransformerAdapter<IMethod, JavaResourceMethod>
	{
		@Override
		public JavaResourceMethod transform(IMethod method) {
			return BinaryType.this.buildMethod(method);
		}
	}

	private IMethod[] getMethods(IType type) {
		try {
			return type.getMethods();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return EMPTY_METHOD_ARRAY;
		}
	}
	
	private static final IMethod[] EMPTY_METHOD_ARRAY = new IMethod[0];
	
	/* CU private */ JavaResourceMethod buildMethod(IMethod jdtMethod) {
		return new BinaryMethod(this, jdtMethod);
	}
	
	protected void updateMethods() {
		throw new UnsupportedOperationException();
	}
	
	public JavaResourceMethod getMethod(String propertyName) {
		for (JavaResourceMethod method : this.getMethods()) {
			if (ObjectTools.equals(method.getMethodName(), propertyName)) {
				return method;
			}
		}
		return null;
	}
	
	
	// ***** inherited field/method types *****
	
	private Map<InheritedAttributeKey, JavaResourceTypeBinding> buildInheritedFieldTypes(ITypeBinding typeBinding) {
		Map<InheritedAttributeKey, JavaResourceTypeBinding> fieldTypes = new HashMap<InheritedAttributeKey, JavaResourceTypeBinding>();
		ITypeBinding scTypeBinding = typeBinding.getSuperclass();
		while (scTypeBinding != null && scTypeBinding.isParameterizedType()) {
			// if the superclass is not parameterized, 
			// then this class will have no increased type information for inherited fields
			buildInheritedFieldTypes_(fieldTypes, scTypeBinding);
			scTypeBinding = scTypeBinding.getSuperclass();
		}
		return fieldTypes;
	}
	
	private void buildInheritedFieldTypes_(
			Map<InheritedAttributeKey, JavaResourceTypeBinding> fieldTypes, ITypeBinding typeBinding) {
		String typeName = typeBinding.getTypeDeclaration().getQualifiedName();
		IVariableBinding[] typeBindingFields = typeBinding.getDeclaredFields();
		for (IVariableBinding field : typeBindingFields) {
			String fieldName = field.getName();
			fieldTypes.put(new InheritedAttributeKey(typeName, fieldName), new JavaResourceTypeBinding(field.getType()));
		}
	}
	
	protected void updateInheritedFieldTypes() {
		throw new UnsupportedOperationException();
	}
	
	private Map<InheritedAttributeKey, JavaResourceTypeBinding> buildInheritedMethodTypes(ITypeBinding typeBinding) {
		Map<InheritedAttributeKey, JavaResourceTypeBinding> methodTypes = new HashMap<InheritedAttributeKey, JavaResourceTypeBinding>();
		ITypeBinding scTypeBinding = typeBinding.getSuperclass();
		while (scTypeBinding != null && scTypeBinding.isParameterizedType()) {
			// if the superclass is not parameterized, 
			// then this class will have no increased type information for inherited fields
			buildInheritedMethodTypes_(methodTypes, scTypeBinding);
			scTypeBinding = scTypeBinding.getSuperclass();
		}
		return methodTypes;
	}
	
	private void buildInheritedMethodTypes_(
			Map<InheritedAttributeKey, JavaResourceTypeBinding> methodTypes, ITypeBinding typeBinding) {
		String typeName = typeBinding.getTypeDeclaration().getQualifiedName();
		IMethodBinding[] typeBindingMethods = typeBinding.getDeclaredMethods();
		for (IMethodBinding method : typeBindingMethods) {
			String methodName = method.getName();
			methodTypes.put(new InheritedAttributeKey(typeName, methodName), new JavaResourceTypeBinding(method.getReturnType()));
		}
	}
	
	protected void updateInheritedMethodTypes() {
		throw new UnsupportedOperationException();
	}
	
	public TypeBinding getAttributeTypeBinding(JavaResourceAttribute attribute) {
		if (attribute.getParent() == this) {
			return attribute.getTypeBinding();
		}
		InheritedAttributeKey key = new InheritedAttributeKey(attribute.getParent().getTypeBinding().getQualifiedName(), attribute.getName());
		if (attribute.getAstNodeType() == JavaResourceAnnotatedElement.AstNodeType.FIELD) {
			return this.inheritedFieldTypes.get(key);
		}
		/* else attribute.getAstNodeType() == JavaResourceAnnotatedElement.AstNodeType.METHOD */
		return this.inheritedMethodTypes.get(key);
	}
	
	
	// ***** misc *****
	
	public boolean hasAnyAnnotatedFields() {
		for (JavaResourceField field : this.getFields()) {
			if (field.isAnnotated()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasAnyAnnotatedMethods() {
		for (JavaResourceMethod method : this.getMethods()) {
			if (method.isAnnotated()) {
				return true;
			}
		}
		return false;
	}
	
	// Two more requirements for a valid equals() method:
	// 1. It should be public 
	// 2. The return type should be boolean
	// Both requirements are validated by the compiler so they are excluded here
	public boolean hasEqualsMethod() {
		for (JavaResourceMethod method : this.getMethods()) {
			if (ObjectTools.equals(method.getMethodName(), "equals") //$NON-NLS-1$
					&& method.getParametersSize() == 1
					&& ObjectTools.equals(method.getParameterTypeName(0), Object.class.getName())) {
				return true;
			}
		}
		return false;
	}
	
	// Two more requirements for a valid hashCode() method:
	// 1. It should be public 
	// 2. The return type should be int
	// Both requirements are validated by the compiler so they are excluded here
	public boolean hasHashCodeMethod() {
		for (JavaResourceMethod method : this.getMethods()) {
			if (ObjectTools.equals(method.getMethodName(), "hashCode") //$NON-NLS-1$
					&& method.getParametersSize() == 0) {
				return true;
			}
		}
		return false;
	}
}
