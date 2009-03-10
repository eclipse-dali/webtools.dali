/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.jar;

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
import org.eclipse.jpt.core.resource.jar.JarResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.jar.JarResourcePersistentType;
import org.eclipse.jpt.core.resource.java.AccessAnnotation;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.NameTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * JAR persistent attribute (field or property)
 */
public class JarResourcePersistentAttributeImpl
	extends AbstractJarResourcePersistentMember
	implements JarResourcePersistentAttribute
{
	private int modifiers;

	private String typeName;

	private boolean typeIsInterface;

	private boolean typeIsEnum;

	private final Vector<String> typeSuperclassNames = new Vector<String>();

	private final Vector<String> typeInterfaceNames = new Vector<String>();

	private final Vector<String> typeTypeArgumentNames = new Vector<String>();


	protected JarResourcePersistentAttributeImpl(JarResourcePersistentType parent, IField field) {
		this(parent, new FieldAdapter(field));
	}

	protected JarResourcePersistentAttributeImpl(JarResourcePersistentType parent, IMethod method) {
		this(parent, new MethodAdapter(method));
	}

	protected JarResourcePersistentAttributeImpl(JarResourcePersistentType parent, Adapter adapter) {
		super(parent, adapter);
		this.modifiers = this.buildModifiers();
		this.typeName = this.buildTypeName();
		this.typeIsInterface = this.buildTypeIsInterface();
		this.typeIsEnum = this.buildTypeIsEnum();
		this.typeSuperclassNames.addAll(this.buildTypeSuperclassNames());
		this.typeInterfaceNames.addAll(this.buildTypeInterfaceNames());
		this.typeTypeArgumentNames.addAll(this.buildTypeTypeArgumentNames());
	}


	// ******** overrides ********

	@Override
	public void update() {
		super.update();
		// TODO
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}


	// ********** AbstractJarResourcePersistentMember implementation **********

	@Override
	protected Adapter getAdapter() {
		return (Adapter) super.getAdapter();
	}

	@Override
	protected Annotation buildMappingAnnotation(IAnnotation jdtAnnotation) {
		return this.getAnnotationProvider().buildAttributeMappingAnnotation(this, jdtAnnotation);
	}

	@Override
	protected Annotation buildSupportingAnnotation(IAnnotation jdtAnnotation) {
		return this.getAnnotationProvider().buildAttributeSupportingAnnotation(this, jdtAnnotation);
	}

	@Override
	protected Annotation buildNullSupportingAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullAttributeSupportingAnnotation(this, annotationName);
	}

	@Override
	protected Annotation buildNullMappingAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullAttributeMappingAnnotation(this, annotationName);
	}

	@Override
	protected ListIterator<String> validMappingAnnotationNames() {
		return this.getAnnotationProvider().attributeMappingAnnotationNames();
	}

	@Override
	protected ListIterator<String> validSupportingAnnotationNames() {
		return this.getAnnotationProvider().attributeSupportingAnnotationNames();
	}


	// ********** JarResourcePersistentAttribute implementation **********

	public String getName() {
		return this.getAdapter().getAttributeName();
	}

	public boolean isField() {
		return this.getAdapter().isField();
	}

	public boolean isProperty() {
		return ! this.isField();
	}

	public boolean hasAnyPersistenceAnnotations() {
		return (this.mappingAnnotationsSize() > 0)
				|| (this.supportingAnnotationsSize() > 0);
	}

	public AccessType getSpecifiedAccess() {
		AccessAnnotation accessAnnotation = (AccessAnnotation) this.getSupportingAnnotation(AccessAnnotation.ANNOTATION_NAME);
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

	protected void setModifiers(int modifiers) {
		int old = this.modifiers;
		this.modifiers = modifiers;
		this.firePropertyChanged(MODIFIERS_PROPERTY, old, modifiers);
	}

	/**
	 * zero seems like a reasonable default...
	 */
	protected int buildModifiers() {
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

	protected void setTypeName(String typeName) {
		String old = this.typeName;
		this.typeName = typeName;
		this.firePropertyChanged(TYPE_NAME_PROPERTY, old, typeName);
	}

	/**
	 * JARs don't have array types;
	 * also, no generic type arguments
	 */
	protected String buildTypeName() {
		return convertTypeSignatureToTypeName(this.getTypeSignature());
	}

	// ***** type is interface
	public boolean typeIsInterface() {
		return this.typeIsInterface;
	}

	protected void setTypeIsInterface(boolean typeIsInterface) {
		boolean old = this.typeIsInterface;
		this.typeIsInterface = typeIsInterface;
		this.firePropertyChanged(TYPE_IS_INTERFACE_PROPERTY, old, typeIsInterface);
	}

	protected boolean buildTypeIsInterface() {
		IType type = this.getType();  // shouldn't be an array...
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

	protected void setTypeIsEnum(boolean typeIsEnum) {
		boolean old = this.typeIsEnum;
		this.typeIsEnum = typeIsEnum;
		this.firePropertyChanged(TYPE_IS_ENUM_PROPERTY, old, typeIsEnum);
	}

	protected boolean buildTypeIsEnum() {
		IType type = this.getType();  // shouldn't be an array...
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

	protected void setTypeSuperclassNames(List<String> typeSuperclassNames) {
		synchronized (this.typeSuperclassNames) {
			this.synchronizeList(typeSuperclassNames, this.typeSuperclassNames, TYPE_SUPERCLASS_NAMES_COLLECTION);
		}
	}

	protected List<String> buildTypeSuperclassNames() {
		IType type = this.getType();
		if (type == null) {
			return Collections.emptyList();
		}

		ArrayList<String> names = new ArrayList<String>();
		type = this.findSuperclass(type);
		while (type != null) {
			names.add(type.getFullyQualifiedName());
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

	protected void setTypeInterfaceNames(Collection<String> typeInterfaceNames) {
		synchronized (this.typeInterfaceNames) {
			this.synchronizeCollection(typeInterfaceNames, this.typeInterfaceNames, TYPE_INTERFACE_NAMES_COLLECTION);
		}
	}

	protected Collection<String> buildTypeInterfaceNames() {
		IType type = this.getType();
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

	protected void addInterfaceNamesTo(IType type, HashSet<String> names) {
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

	protected void setTypeTypeArgumentNames(List<String> typeTypeArgumentNames) {
		synchronized (this.typeTypeArgumentNames) {
			this.synchronizeList(typeTypeArgumentNames, this.typeTypeArgumentNames, TYPE_TYPE_ARGUMENT_NAMES_COLLECTION);
		}
	}

	/**
	 * these types can be arrays (e.g. "java.lang.String[]");
	 * but they won't have any further nested generic type arguments
	 * (e.g. "java.util.Collection<java.lang.String>")
	 */
	protected List<String> buildTypeTypeArgumentNames() {
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

	protected String getTypeSignature() {
		try {
			return this.getAdapter().getTypeSignature();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return null;
		}
	}

	protected IType findSuperclass(IType type) {
		return this.findTypeBySignature(this.getSuperclassSignature(type));
	}

	protected String getSuperclassSignature(IType type) {
		try {
			return type.getSuperclassTypeSignature();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return null;
		}
	}

	protected String[] getSuperInterfaceTypeSignatures(IType type) {
		try {
			return type.getSuperInterfaceTypeSignatures();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return EMPTY_STRING_ARRAY;
		}
	}
	protected static final String[] EMPTY_STRING_ARRAY = new String[0];

	/**
	 * Strip off the type signature's parameters if present.
	 * Convert to a readable string.
	 */
	protected static String convertTypeSignatureToTypeName(String typeSignature) {
		return (typeSignature == null) ? null : convertTypeSignatureToTypeName_(typeSignature);
	}

	/**
	 * no null check
	 */
	protected static String convertTypeSignatureToTypeName_(String typeSignature) {
		return Signature.toString(Signature.getTypeErasure(typeSignature));
	}

	protected IType findTypeBySignature(String typeSignature) {
		return (typeSignature == null) ? null : this.findType(convertTypeSignatureToTypeName_(typeSignature));
	}

	protected IType getType() {
		return (this.typeName == null) ? null : this.findType(this.typeName);
	}

	protected IType findType(String fullyQualifiedName) {
		try {
			return this.getJavaProject().findType(fullyQualifiedName);
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return null;
		}
	}

	protected IJavaProject getJavaProject() {
		return this.getMember().getJavaProject();
	}


	// ********** adapters **********

	protected interface Adapter extends AbstractJarResourcePersistentMember.Adapter {
		String getAttributeName();
		boolean isField();
		String getTypeSignature() throws JavaModelException;
	}

	protected static class FieldAdapter implements Adapter {
		private final IField field;

		protected FieldAdapter(IField field) {
			super();
			this.field = field;
		}

		public IField getMember() {
			return this.field;
		}

		public boolean isPersistable() {
			return this.field.exists() && JPTTools.fieldIsPersistable(new JPTToolsAdapter(this.field));
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
		protected static class JPTToolsAdapter implements JPTTools.FieldAdapter {
			private final IField field;

			protected JPTToolsAdapter(IField field) {
				super();
				if (field == null) {
					throw new NullPointerException();
				}
				this.field = field;
			}

			public int getModifiers() {
				try {
					return this.field.getFlags();
				} catch (JavaModelException ex) {
					JptCorePlugin.log(ex);
					return 0;
				}
			}

		}

	}

	protected static class MethodAdapter implements Adapter {
		private final IMethod method;

		protected MethodAdapter(IMethod method) {
			super();
			this.method = method;
		}

		public IMethod getMember() {
			return this.method;
		}

		public boolean isPersistable() {
			return JPTTools.methodIsPersistablePropertyGetter(new JPTToolsAdapter(this.method));
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
		protected static class JPTToolsAdapter implements JPTTools.MethodAdapter {
			private final IMethod method;

			protected JPTToolsAdapter(IMethod method) {
				super();
				if (method == null) {
					throw new NullPointerException();
				}
				this.method = method;
			}

			public String getName() {
				return this.method.getElementName();
			}

			public int getModifiers() {
				try {
					return this.method.getFlags();
				} catch (JavaModelException ex) {
					JptCorePlugin.log(ex);
					return 0;
				}
			}

			public String getReturnTypeName() {
				try {
					return this.method.getReturnType();
				} catch (JavaModelException ex) {
					JptCorePlugin.log(ex);
					return null;
				}
			}

			public boolean isConstructor() {
				try {
					return this.method.isConstructor();
				} catch (JavaModelException ex) {
					JptCorePlugin.log(ex);
					return false;
				}
			}

			public int getParametersLength() {
				return this.method.getParameterTypes().length;
			}

			public JPTTools.MethodAdapter getSibling(String name) {
				for (IMethod sibling : this.getSiblings()) {
					if ((sibling.getParameterTypes().length == 0)
							&& sibling.getElementName().equals(name)) {
						return new JPTToolsAdapter(sibling);
					}
				}
				return null;
			}

			public JPTTools.MethodAdapter getSibling(String name, String parameterTypeName) {
				for (IMethod sibling : this.getSiblings()) {
					String[] parmTypes = sibling.getParameterTypes();
					if ((parmTypes.length == 1)
							&& parmTypes[0].equals(parameterTypeName)
							&& sibling.getElementName().equals(name)) {
						return new JPTToolsAdapter(sibling);
					}
				}
				return null;
			}

			protected IMethod[] getSiblings() {
				try {
					return this.method.getDeclaringType().getMethods();
				} catch (JavaModelException ex) {
					JptCorePlugin.log(ex);
					return EMPTY_METHOD_ARRAY;
				}
			}
			protected static final IMethod[] EMPTY_METHOD_ARRAY = new IMethod[0];

		}

	}

	// TODO ============== remove ========================
	public boolean isFor(MethodSignature methodSignature, int occurrence) {
		throw new UnsupportedOperationException();
	}

}
