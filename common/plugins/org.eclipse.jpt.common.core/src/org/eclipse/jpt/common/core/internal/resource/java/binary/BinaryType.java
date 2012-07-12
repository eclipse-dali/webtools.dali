/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.resource.java.CounterMap;
import org.eclipse.jpt.common.core.internal.resource.java.InheritedAttributeKey;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.JavaResourceTypeBinding;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;

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
	
	private final Vector<JavaResourceField> fields;
	
	private final Vector<JavaResourceMethod> methods;
	
	private final Map<InheritedAttributeKey, JavaResourceTypeBinding> inheritedFieldTypes;
	
	private final Map<InheritedAttributeKey, JavaResourceTypeBinding> inheritedMethodTypes;
	
	
	// ***** construction/initialization *****
	
	BinaryType(JavaResourceNode parent, IType type) {
		super(parent, type);
		this.superclassQualifiedName = buildSuperclassQualifiedName(type);
		this.abstract_ = buildAbstract(type);
		this.hasNoArgConstructor = buildHasNoArgConstructor(type);
		this.hasPrivateNoArgConstructor = buildHasPrivateNoArgConstructor(type);
		this.fields = buildFields(type);
		this.methods = buildMethods(type);
		
		ITypeBinding typeBinding = (ITypeBinding) ASTTools.createBinding(type);
		this.inheritedFieldTypes = buildInheritedFieldTypes(typeBinding);
		this.inheritedMethodTypes = buildInheritedMethodTypes(typeBinding);
	}
	
	
	public Kind getKind() {
		return JavaResourceAnnotatedElement.Kind.TYPE;
	}
	
	
	// ***** overrides *****
	
	@Override
	protected void update(IMember member) {
		super.update(member);
		this.setSuperclassQualifiedName(this.buildSuperclassQualifiedName((IType) member));
		this.setAbstract(this.buildAbstract((IType) member));
		this.setHasNoArgConstructor(this.buildHasNoArgConstructor((IType) member));
		this.setHasPrivateNoArgConstructor(this.buildHasPrivateNoArgConstructor((IType) member));
		this.updateFields((IType) member);
		this.updateMethods((IType) member);
	}
	
	// TODO
	private void updateFields(IType type) {
		throw new UnsupportedOperationException();
	}
	
	// TODO
	private void updateMethods(IType type) {
		throw new UnsupportedOperationException();
	}
	
	
	// ********** JavaResourceType implementation **********
	
	// ***** superclass qualified name
	public String getSuperclassQualifiedName() {
		return this.superclassQualifiedName;
	}
	
	private void setSuperclassQualifiedName(String superclassQualifiedName) {
		String old = this.superclassQualifiedName;
		this.superclassQualifiedName = superclassQualifiedName;
		this.firePropertyChanged(SUPERCLASS_QUALIFIED_NAME_PROPERTY, old, superclassQualifiedName);
	}
	
	private String buildSuperclassQualifiedName(IType type) {
		return convertTypeSignatureToTypeName(this.getSuperclassTypeSignature(type));
	}
	
	private String getSuperclassTypeSignature(IType type) {
		try {
			return type.getSuperclassTypeSignature();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return null;
		}
	}
	
	// ***** abstract
	public boolean isAbstract() {
		return this.abstract_;
	}
	
	private void setAbstract(boolean abstract_) {
		boolean old = this.abstract_;
		this.abstract_ = abstract_;
		this.firePropertyChanged(ABSTRACT_PROPERTY, old, abstract_);
	}
	
	private boolean buildAbstract(IType type) {
		try {
			return Flags.isAbstract(type.getFlags());
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}
	
	// ***** no-arg constructor
	public boolean hasNoArgConstructor() {
		return this.hasNoArgConstructor;
	}
	
	private void setHasNoArgConstructor(boolean hasNoArgConstructor) {
		boolean old = this.hasNoArgConstructor;
		this.hasNoArgConstructor = hasNoArgConstructor;
		this.firePropertyChanged(NO_ARG_CONSTRUCTOR_PROPERTY, old, hasNoArgConstructor);
	}
	
	private boolean buildHasNoArgConstructor(IType type) {
		return this.findNoArgConstructor(type) != null;
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
			JptCommonCorePlugin.log(ex);
		}
		return null;
	}
	
	// ***** private no-arg constructor
	public boolean hasPrivateNoArgConstructor() {
		return this.hasPrivateNoArgConstructor;
	}
	
	private void setHasPrivateNoArgConstructor(boolean hasPrivateNoArgConstructor) {
		boolean old = this.hasPrivateNoArgConstructor;
		this.hasPrivateNoArgConstructor = hasPrivateNoArgConstructor;
		this.firePropertyChanged(PRIVATE_NO_ARG_CONSTRUCTOR_PROPERTY, old, hasPrivateNoArgConstructor);
	}
	
	private boolean buildHasPrivateNoArgConstructor(IType type) {
		IMethod method = this.findNoArgConstructor(type);
		try {
			return method != null && Flags.isPrivate(method.getFlags());
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}
	
	
	// ***** public/protected no-arg constructor *****
	
	public boolean hasPublicOrProtectedNoArgConstructor() {
		Iterable<JavaResourceMethod> constructors = this.getConstructors();
		if (CollectionTools.size(constructors) == 0) {
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
		if (CollectionTools.size(constructors) == 0) {
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
	
	@Override
	public IType getMember() {
		return super.getMember();
	}
	
	// Two more requirements for a valid equals() method:
	// 1. It should be public 
	// 2. The return type should be boolean
	// Both requirements are validated by the compiler so they are excluded here
	public boolean hasEqualsMethod() {
		for (JavaResourceMethod method : this.getMethods()) {
			if (StringTools.stringsAreEqual(method.getMethodName(), "equals") //$NON-NLS-1$
					&& method.getParametersSize() == 1
					&& StringTools.stringsAreEqual(method.getParameterTypeName(0), Object.class.getName())) {
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
			if (StringTools.stringsAreEqual(method.getMethodName(), "hashCode") //$NON-NLS-1$
					&& method.getParametersSize() == 0) {
				return true;
			}
		}
		return false;
	}
	
	public JavaResourceMethod getMethod(String propertyName) {
		for (JavaResourceMethod method : this.getMethods()) {
			if (StringTools.stringsAreEqual(method.getMethodName(), propertyName)) {
				return method;
			}
		}
		return null;
	}
	
	
	// ***** fields *****
	
	public Iterable<JavaResourceField> getFields() {
		return new LiveCloneIterable<JavaResourceField>(this.fields);
	}
	
	private Vector<JavaResourceField> buildFields(IType type) {
		IField[] jdtFields = this.getFields(type);
		Vector<JavaResourceField> result = new Vector<JavaResourceField>(jdtFields.length);
		for (IField jdtField : jdtFields) {
			result.add(this.buildField(jdtField));
		}
		return result;
	}
	
	private IField[] getFields(IType type) {
		try {
			return type.getFields();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return EMPTY_FIELD_ARRAY;
		}
	}
	
	private static final IField[] EMPTY_FIELD_ARRAY = new IField[0];
	
	private JavaResourceField buildField(IField jdtField) {
		return new BinaryField(this, jdtField);
	}
	
	
	// ***** methods *****
	
	public Iterable<JavaResourceMethod> getMethods() {
		return new LiveCloneIterable<JavaResourceMethod>(this.methods);
	}
	
	private Vector<JavaResourceMethod> buildMethods(IType type) {
		IMethod[] jdtMethods = this.getMethods(type);
		Vector<JavaResourceMethod> result = new Vector<JavaResourceMethod>(jdtMethods.length);
		for (IMethod jdtMethod : jdtMethods) {
			result.add(this.buildMethod(jdtMethod));
		}
		return result;
	}
	
	private IMethod[] getMethods(IType type) {
		try {
			return type.getMethods();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return EMPTY_METHOD_ARRAY;
		}
	}
	
	private static final IMethod[] EMPTY_METHOD_ARRAY = new IMethod[0];
	
	private JavaResourceMethod buildMethod(IMethod jdtMethod) {
		return new BinaryMethod(this, jdtMethod);
	}
	
	
	// ***** inherited field/method types *****
	
	private Map<InheritedAttributeKey, JavaResourceTypeBinding> buildInheritedFieldTypes(ITypeBinding typeBinding) {
		Map<InheritedAttributeKey, JavaResourceTypeBinding> inheritedFieldTypes = new Hashtable<InheritedAttributeKey, JavaResourceTypeBinding>();
		ITypeBinding scTypeBinding = typeBinding.getSuperclass();
		while (scTypeBinding != null && ! scTypeBinding.isParameterizedType()) {
			// if the superclass is not parameterized, 
			// then this class will have no increased type information for inherited fields
			buildInheritedFieldTypes_(inheritedFieldTypes, scTypeBinding);
			scTypeBinding = scTypeBinding.getSuperclass();
		}
		return inheritedFieldTypes;
	}
	
	private void buildInheritedFieldTypes_(
			Map<InheritedAttributeKey, JavaResourceTypeBinding> inheritedFieldTypes, ITypeBinding typeBinding) {
		String typeName = typeBinding.getQualifiedName();
		IVariableBinding[] fields = typeBinding.getDeclaredFields();
		CounterMap counters = new CounterMap(fields.length);
		for (IVariableBinding field : fields) {
			String fieldName = field.getName();
			int occurrence = counters.increment(fieldName);
			inheritedFieldTypes.put(new InheritedAttributeKey(typeName, fieldName, occurrence), new JavaResourceTypeBinding(field.getType()));
		}
	}
	
	private Map<InheritedAttributeKey, JavaResourceTypeBinding> buildInheritedMethodTypes(ITypeBinding typeBinding) {
		Map<InheritedAttributeKey, JavaResourceTypeBinding> inheritedMethodTypes = new Hashtable<InheritedAttributeKey, JavaResourceTypeBinding>();
		ITypeBinding scTypeBinding = typeBinding.getSuperclass();
		while (scTypeBinding != null && ! scTypeBinding.isParameterizedType()) {
			// if the superclass is not parameterized, 
			// then this class will have no increased type information for inherited fields
			buildInheritedMethodTypes_(inheritedMethodTypes, scTypeBinding);
			scTypeBinding = scTypeBinding.getSuperclass();
		}
		return inheritedMethodTypes;
	}
	
	private void buildInheritedMethodTypes_(
			Map<InheritedAttributeKey, JavaResourceTypeBinding> inheritedFieldTypes, ITypeBinding typeBinding) {
		String typeName = typeBinding.getQualifiedName();
		IMethodBinding[] methods = typeBinding.getDeclaredMethods();
		CounterMap counters = new CounterMap(methods.length);
		for (IMethodBinding method : methods) {
			String methodName = method.getName();
			int occurrence = counters.increment(methodName);
			inheritedFieldTypes.put(new InheritedAttributeKey(typeName, methodName, occurrence), new JavaResourceTypeBinding(method.getReturnType()));
		}
	}
}
