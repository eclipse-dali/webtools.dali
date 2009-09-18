/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.utility.jdt.JPTTools;
import org.eclipse.jpt.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.NameTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * binary persistent attribute (field or property)
 */
final class BinaryPersistentAttribute
	extends BinaryPersistentMember
	implements JavaResourcePersistentAttribute
{
	private int modifiers;

	private String typeName;

	private boolean typeIsInterface;

	private boolean typeIsEnum;

	private final Vector<String> typeSuperclassNames = new Vector<String>();

	private final Vector<String> typeInterfaceNames = new Vector<String>();

	private final Vector<String> typeTypeArgumentNames = new Vector<String>();


	BinaryPersistentAttribute(JavaResourcePersistentType parent, IField field) {
		this(parent, new FieldAdapter(field));
	}

	BinaryPersistentAttribute(JavaResourcePersistentType parent, IMethod method) {
		this(parent, new MethodAdapter(method));
	}

	private BinaryPersistentAttribute(JavaResourcePersistentType parent, Adapter adapter) {
		super(parent, adapter);
		this.modifiers = this.buildModifiers();
		this.typeName = this.buildTypeName();

		IType type = this.getType();  // shouldn't be an array...
		this.typeIsInterface = this.buildTypeIsInterface(type);
		this.typeIsEnum = this.buildTypeIsEnum(type);
		this.typeSuperclassNames.addAll(this.buildTypeSuperclassNames(type));
		this.typeInterfaceNames.addAll(this.buildTypeInterfaceNames(type));

		this.typeTypeArgumentNames.addAll(this.buildTypeTypeArgumentNames());
	}


	// ******** overrides ********

	@Override
	public void update() {
		super.update();
		this.setModifiers(this.buildModifiers());
		this.setTypeName(this.buildTypeName());

		IType type = this.getType();  // shouldn't be an array...
		this.setTypeIsInterface(this.buildTypeIsInterface(type));
		this.setTypeIsEnum(this.buildTypeIsEnum(type));
		this.setTypeSuperclassNames(this.buildTypeSuperclassNames(type));
		this.setTypeInterfaceNames(this.buildTypeInterfaceNames(type));

		this.setTypeTypeArgumentNames(this.buildTypeTypeArgumentNames());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}


	// ********** BinaryPersistentMember implementation **********
	
	private Adapter getAdapter() {
		return (Adapter) this.adapter;
	}
	
	@Override
	Iterator<String> validAnnotationNames() {
		return this.getAnnotationProvider().attributeAnnotationNames();
	}
	
	@Override
	Annotation buildAnnotation(IAnnotation jdtAnnotation) {
		return this.getAnnotationProvider().buildAttributeAnnotation(this, jdtAnnotation);
	}
	
	Annotation buildNullAnnotation_(String annotationName) {
		return this.getAnnotationProvider().buildNullAttributeAnnotation(this, annotationName);
	}
	
	
	// ********** JavaResourcePersistentAttribute implementation **********
	
	public String getName() {
		return this.getAdapter().getAttributeName();
	}
	
	@Override
	public Annotation buildNullAnnotation(String annotationName) {
		return (annotationName == null) ? null : this.buildNullAnnotation(annotationName);
	}
	
	public boolean isField() {
		return this.getAdapter().isField();
	}

	public boolean isProperty() {
		return ! this.isField();
	}

	public boolean isFor(MethodSignature methodSignature, int occurrence) {
		throw new UnsupportedOperationException();
	}

	public AccessType getSpecifiedAccess() {
		Access2_0Annotation accessAnnotation = (Access2_0Annotation) this.getAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		return accessAnnotation == null ? null : accessAnnotation.getValue();
	}

	public boolean typeIsSubTypeOf(String tn) {
		return ((this.typeName != null) && this.typeName.equals(tn))
				|| this.typeInterfaceNames.contains(tn)
				|| this.typeSuperclassNames.contains(tn);
	}

	public boolean typeIsVariablePrimitive() {
		return (this.typeName != null) && ClassTools.classNamedIsVariablePrimitive(this.typeName);
	}

	// ***** modifiers
	public int getModifiers() {
		return this.modifiers;
	}

	private void setModifiers(int modifiers) {
		int old = this.modifiers;
		this.modifiers = modifiers;
		this.firePropertyChanged(MODIFIERS_PROPERTY, old, modifiers);
	}

	/**
	 * zero seems like a reasonable default...
	 */
	private int buildModifiers() {
		try {
			return this.getMember().getFlags();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return 0;
		}
	}

	// ***** type name
	public String getTypeName() {
		return this.typeName;
	}

	private void setTypeName(String typeName) {
		String old = this.typeName;
		this.typeName = typeName;
		this.firePropertyChanged(TYPE_NAME_PROPERTY, old, typeName);
	}

	/**
	 * JARs don't have array types;
	 * also, no generic type parameters
	 */
	private String buildTypeName() {
		return convertTypeSignatureToTypeName(this.getTypeSignature());
	}

	// ***** type is interface
	public boolean typeIsInterface() {
		return this.typeIsInterface;
	}

	private void setTypeIsInterface(boolean typeIsInterface) {
		boolean old = this.typeIsInterface;
		this.typeIsInterface = typeIsInterface;
		this.firePropertyChanged(TYPE_IS_INTERFACE_PROPERTY, old, typeIsInterface);
	}

	private boolean buildTypeIsInterface(IType type) {
		try {
			return (type != null) && type.isInterface();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return false;
		}
	}

	// ***** type is enum
	public boolean typeIsEnum() {
		return this.typeIsEnum;
	}

	private void setTypeIsEnum(boolean typeIsEnum) {
		boolean old = this.typeIsEnum;
		this.typeIsEnum = typeIsEnum;
		this.firePropertyChanged(TYPE_IS_ENUM_PROPERTY, old, typeIsEnum);
	}

	private boolean buildTypeIsEnum(IType type) {
		try {
			return (type != null) && type.isEnum();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return false;
		}
	}

	// ***** type superclass hierarchy
	public ListIterator<String> typeSuperclassNames() {
		return new CloneListIterator<String>(this.typeSuperclassNames);
	}

	public boolean typeSuperclassNamesContains(String superclassName) {
		return this.typeSuperclassNames.contains(superclassName);
	}

	private void setTypeSuperclassNames(List<String> typeSuperclassNames) {
		this.synchronizeList(typeSuperclassNames, this.typeSuperclassNames, TYPE_SUPERCLASS_NAMES_LIST);
	}

	private List<String> buildTypeSuperclassNames(IType type) {
		if (type == null) {
			return Collections.emptyList();
		}

		ArrayList<String> names = new ArrayList<String>();
		type = this.findSuperclass(type);
		while (type != null) {
			names.add(type.getFullyQualifiedName('.'));  // no parameters are included here
			type = this.findSuperclass(type);
		}
		return names;
	}

	// ***** type interface hierarchy
	public Iterator<String> typeInterfaceNames() {
		return new CloneIterator<String>(this.typeInterfaceNames);
	}

	public boolean typeInterfaceNamesContains(String interfaceName) {
		return this.typeInterfaceNames.contains(interfaceName);
	}

	private void setTypeInterfaceNames(Collection<String> typeInterfaceNames) {
		this.synchronizeCollection(typeInterfaceNames, this.typeInterfaceNames, TYPE_INTERFACE_NAMES_COLLECTION);
	}

	private Collection<String> buildTypeInterfaceNames(IType type) {
		if (type == null) {
			return Collections.emptySet();
		}

		HashSet<String> names = new HashSet<String>();
		while (type != null) {
			this.addInterfaceNamesTo(type, names);
			type = this.findSuperclass(type);
		}
		return names;
	}

	private void addInterfaceNamesTo(IType type, HashSet<String> names) {
		for (String interfaceSignature : this.getSuperInterfaceTypeSignatures(type)) {
			String interfaceName = convertTypeSignatureToTypeName(interfaceSignature);
			names.add(interfaceName);
			IType interfaceType = this.findType(interfaceName);
			if (interfaceType != null) {
				this.addInterfaceNamesTo(interfaceType, names);  // recurse
			}
		}
	}

	// ***** type type argument names
	public ListIterator<String> typeTypeArgumentNames() {
		return new CloneListIterator<String>(this.typeTypeArgumentNames);
	}

	public int typeTypeArgumentNamesSize() {
		return this.typeTypeArgumentNames.size();
	}

	public String getTypeTypeArgumentName(int index) {
		return this.typeTypeArgumentNames.get(index);
	}

	private void setTypeTypeArgumentNames(List<String> typeTypeArgumentNames) {
		this.synchronizeList(typeTypeArgumentNames, this.typeTypeArgumentNames, TYPE_TYPE_ARGUMENT_NAMES_LIST);
	}

	/**
	 * these types can be arrays (e.g. "java.lang.String[]");
	 * but they won't have any further nested generic type arguments
	 * (e.g. "java.util.Collection<java.lang.String>")
	 */
	private List<String> buildTypeTypeArgumentNames() {
		String typeSignature = this.getTypeSignature();
		if (typeSignature == null) {
			return Collections.emptyList();
		}

		String[] typeArgumentSignatures = Signature.getTypeArguments(typeSignature);
		if (typeArgumentSignatures.length == 0) {
			return Collections.emptyList();
		}

		ArrayList<String> names = new ArrayList<String>(typeArgumentSignatures.length);
		for (String typeArgumentSignature : typeArgumentSignatures) {
			names.add(convertTypeSignatureToTypeName(typeArgumentSignature));
		}
		return names;
	}


	// ********** convenience methods **********

	private String getTypeSignature() {
		try {
			return this.getAdapter().getTypeSignature();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return null;
		}
	}

	private IType findSuperclass(IType type) {
		return this.findTypeBySignature(this.getSuperclassSignature(type));
	}

	private String getSuperclassSignature(IType type) {
		try {
			return type.getSuperclassTypeSignature();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return null;
		}
	}

	private String[] getSuperInterfaceTypeSignatures(IType type) {
		try {
			return type.getSuperInterfaceTypeSignatures();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return EMPTY_STRING_ARRAY;
		}
	}
	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	private IType findTypeBySignature(String typeSignature) {
		return (typeSignature == null) ? null : this.findType(convertTypeSignatureToTypeName_(typeSignature));
	}

	private IType getType() {
		return (this.typeName == null) ? null : this.findType(this.typeName);
	}

	private IType findType(String fullyQualifiedName) {
		try {
			return this.getJavaProject().findType(fullyQualifiedName);
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return null;
		}
	}

	private IJavaProject getJavaProject() {
		return this.getMember().getJavaProject();
	}



	// ********** adapters **********

	/**
	 * Adapt an IField or IMethod.
	 */
	interface Adapter
		extends BinaryPersistentMember.Adapter
	{
		/**
		 * Return the field or getter method's "attribute" name
		 * (e.g. field "foo" -> "foo"; method "getFoo" -> "foo").
		 */
		String getAttributeName();

		/**
		 * Return whether the attribute is a Java field (as opposed to a method).
		 */
		boolean isField();

		/**
		 * Return the attribute's type signature.
		 */
		String getTypeSignature() throws JavaModelException;
	}

	/**
	 * IField adapter
	 */
	static class FieldAdapter
		implements Adapter
	{
		final IField field;

		FieldAdapter(IField field) {
			super();
			this.field = field;
		}

		public IField getMember() {
			return this.field;
		}

		public boolean isPersistable() {
			return this.field.exists() && JPTTools.fieldIsPersistable(new JPTToolsAdapter());
		}

		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.field.getAnnotations();
		}

		public String getAttributeName() {
			return this.field.getElementName();
		}

		public boolean isField() {
			return true;
		}

		public String getTypeSignature() throws JavaModelException {
			return this.field.getTypeSignature();
		}

		/**
		 * JPTTools needs an adapter so it can work with either an IField
		 * or an IVariableBinding etc.
		 */
		class JPTToolsAdapter implements JPTTools.FieldAdapter {
			public int getModifiers() {
				try {
					return FieldAdapter.this.field.getFlags();
				} catch (JavaModelException ex) {
					JptCorePlugin.log(ex);
					return 0;
				}
			}

		}

	}

	/**
	 * IMethod adapter
	 */
	static class MethodAdapter
		implements Adapter
	{
		final IMethod method;
		static final IMethod[] EMPTY_METHOD_ARRAY = new IMethod[0];

		MethodAdapter(IMethod method) {
			super();
			this.method = method;
		}

		public IMethod getMember() {
			return this.method;
		}

		public boolean isPersistable() {
			return JPTTools.methodIsPersistablePropertyGetter(new JPTToolsAdapter());
		}

		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.method.getAnnotations();
		}

		public String getAttributeName() {
			return NameTools.convertGetterMethodNameToPropertyName(this.method.getElementName());
		}

		public boolean isField() {
			return false;
		}

		public String getTypeSignature() throws JavaModelException {
			return this.method.getReturnType();
		}

		/**
		 * JPTTools needs an adapter so it can work with either an IMethod
		 * or an IMethodBinding etc.
		 */
		abstract static class AbstractJPTToolsAdapter
			implements JPTTools.SimpleMethodAdapter
		{
			AbstractJPTToolsAdapter() {
				super();
			}

			abstract IMethod getMethod();

			public int getModifiers() {
				try {
					return this.getMethod().getFlags();
				} catch (JavaModelException ex) {
					JptCorePlugin.log(ex);
					return 0;
				}
			}

			public String getReturnTypeErasureName() {
				return convertTypeSignatureToTypeName(this.getReturnTypeSignature());
			}

			private String getReturnTypeSignature() {
				try {
					return this.getMethod().getReturnType();
				} catch (JavaModelException ex) {
					JptCorePlugin.log(ex);
					return null;
				}
			}

			public boolean isConstructor() {
				try {
					return this.getMethod().isConstructor();
				} catch (JavaModelException ex) {
					JptCorePlugin.log(ex);
					return false;
				}
			}

		}

		static class SimpleJPTToolsAdapter
			extends AbstractJPTToolsAdapter
		{
			private final IMethod method;

			SimpleJPTToolsAdapter(IMethod method) {
				super();
				this.method = method;
			}

			@Override
			IMethod getMethod() {
				return this.method;
			}

		}

		class JPTToolsAdapter
			extends AbstractJPTToolsAdapter
			implements JPTTools.MethodAdapter
		{
			JPTToolsAdapter() {
				super();
			}

			@Override
			IMethod getMethod() {
				return MethodAdapter.this.method;
			}

			public String getName() {
				return this.getMethod().getElementName();
			}

			public int getParametersLength() {
				return this.getMethod().getParameterTypes().length;
			}

			public JPTTools.SimpleMethodAdapter getSibling(String name) {
				for (IMethod sibling : this.getSiblings()) {
					if ((sibling.getParameterTypes().length == 0)
							&& sibling.getElementName().equals(name)) {
						return new SimpleJPTToolsAdapter(sibling);
					}
				}
				return null;
			}

			public JPTTools.SimpleMethodAdapter getSibling(String name, String parameterTypeErasureName) {
				for (IMethod sibling : this.getSiblings()) {
					String[] parmTypes = sibling.getParameterTypes();
					if ((parmTypes.length == 1)
							&& sibling.getElementName().equals(name)
							&& convertTypeSignatureToTypeName(parmTypes[0]).equals(parameterTypeErasureName)) {
						return new SimpleJPTToolsAdapter(sibling);
					}
				}
				return null;
			}

			private IMethod[] getSiblings() {
				try {
					return this.getMethod().getDeclaringType().getMethods();
				} catch (JavaModelException ex) {
					JptCorePlugin.log(ex);
					return EMPTY_METHOD_ARRAY;
				}
			}

		}

	}

}
