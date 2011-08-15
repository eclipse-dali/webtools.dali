/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.ClassName;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;

/**
 * binary attribute (field or property)
 */
abstract class BinaryAttribute
	extends BinaryMember
	implements JavaResourceAttribute
{
	private int modifiers;

	private String typeName;

	private boolean typeIsInterface;

	private boolean typeIsEnum;

	private boolean typeIsArray;
	
	private int typeArrayDimensionality;
	
	private String typeArrayComponentTypeName;

	private final Vector<String> typeSuperclassNames = new Vector<String>();

	private final Vector<String> typeInterfaceNames = new Vector<String>();

	private final Vector<String> typeTypeArgumentNames = new Vector<String>();


	protected BinaryAttribute(JavaResourceType parent, Adapter adapter) {
		super(parent, adapter);
		this.modifiers = this.buildModifiers();
		
		String typeSignature = getTypeSignature();
		Iterable<ITypeParameter> typeParameters = getAdapter().getTypeParameters();
		this.typeName = buildTypeName(typeSignature, typeParameters);
		this.typeIsArray = buildTypeIsArray(typeSignature);
		this.typeArrayDimensionality = buildTypeArrayDimensionality(typeSignature);
		this.typeArrayComponentTypeName = buildTypeArrayComponentTypeName(typeSignature, typeParameters);
		this.typeTypeArgumentNames.addAll(buildTypeTypeArgumentNames(typeSignature, typeParameters));
		
		IType type = getType();
		// if the type is an array, then the following will be false or empty
		this.typeIsInterface = this.buildTypeIsInterface(type);
		this.typeIsEnum = this.buildTypeIsEnum(type);
		this.typeSuperclassNames.addAll(this.buildTypeSuperclassNames(type));
		this.typeInterfaceNames.addAll(this.buildTypeInterfaceNames(type));
	}


	// ******** overrides ********

	@Override
	public void update() {
		super.update();
		this.setModifiers(this.buildModifiers());
		
		String typeSignature = getTypeSignature();
		Iterable<ITypeParameter> typeParameters = getAdapter().getTypeParameters();
		setTypeName(buildTypeName(typeSignature, typeParameters));
		setTypeIsArray(buildTypeIsArray(typeSignature));
		setTypeArrayDimensionality(buildTypeArrayDimensionality(typeSignature));
		setTypeArrayComponentTypeName(buildTypeArrayComponentTypeName(typeSignature, typeParameters));
		setTypeTypeArgumentNames(buildTypeTypeArgumentNames(typeSignature, typeParameters));
		
		IType type = this.getType();  // if the type is an array, then the following will be false or empty
		this.setTypeIsInterface(this.buildTypeIsInterface(type));
		this.setTypeIsEnum(this.buildTypeIsEnum(type));
		this.setTypeSuperclassNames(this.buildTypeSuperclassNames(type));
		this.setTypeInterfaceNames(this.buildTypeInterfaceNames(type));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}


	// ********** BinaryPersistentMember implementation **********
	
	private Adapter getAdapter() {
		return (Adapter) this.adapter;
	}
	
	
	// ********** JavaResourceAttribute implementation **********
	
	@Override
	public JavaResourceType getParent() {
		return (JavaResourceType) super.getParent();
	}

	public JavaResourceType getResourceType() {
		return this.getParent();
	}

	public String getName() {
		return this.getAdapter().getAttributeName();
	}

	public boolean typeIsSubTypeOf(String tn) {
		return ((this.typeName != null) && this.typeName.equals(tn))
				|| this.typeInterfaceNames.contains(tn)
				|| this.typeSuperclassNames.contains(tn);
	}

	public boolean typeIsVariablePrimitive() {
		return (this.typeName != null) && ClassName.isVariablePrimitive(this.typeName);
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
			JptCommonCorePlugin.log(ex);
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
	
	private String buildTypeName(String typeSignature, Iterable<ITypeParameter> typeParameters) {
		return convertTypeSignatureToTypeName(typeSignature, typeParameters);
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
			JptCommonCorePlugin.log(ex);
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
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	// ***** type is array
	public boolean typeIsArray() {
		return this.typeIsArray;
	}

	private void setTypeIsArray(boolean typeIsArray) {
		boolean old = this.typeIsArray;
		this.typeIsArray = typeIsArray;
		this.firePropertyChanged(TYPE_IS_ARRAY_PROPERTY, old, typeIsArray);
	}

	private boolean buildTypeIsArray(String typeSignature) {
		return convertTypeSignatureToTypeIsArray(typeSignature);
	}
	
	// ***** type array dimensionality
	public int getTypeArrayDimensionality() {
		return this.typeArrayDimensionality;
	}
	
	private void setTypeArrayDimensionality(int typeArrayDimensionality) {
		int old = this.typeArrayDimensionality;
		this.typeArrayDimensionality = typeArrayDimensionality;
		firePropertyChanged(TYPE_ARRAY_DIMENSIONALITY_PROPERTY, old, typeArrayDimensionality);
	}
	
	private int buildTypeArrayDimensionality(String typeSignature) {
		return convertTypeSignatureToTypeArrayDimensionality(typeSignature);
 	}
	
	// ***** type array component type name
	public String getTypeArrayComponentTypeName() {
		return this.typeArrayComponentTypeName;
	}
	
	private void setTypeArrayComponentTypeName(String typeArrayComponentTypeName) {
		String old = this.typeArrayComponentTypeName;
		this.typeArrayComponentTypeName = typeArrayComponentTypeName;
		firePropertyChanged(TYPE_ARRAY_COMPONENT_TYPE_NAME_PROPERTY, old, typeArrayComponentTypeName);
	}
	
	private String buildTypeArrayComponentTypeName(String typeSignature, Iterable<ITypeParameter> typeParameters) {
		int arrayDimensionality = convertTypeSignatureToTypeArrayDimensionality(typeSignature);
		return (arrayDimensionality == 0) ? null : convertTypeSignatureToTypeArrayComponentTypeName(typeSignature, typeParameters);
 	}

	// ***** type superclass hierarchy
	public ListIterable<String> getTypeSuperclassNames() {
		return new LiveCloneListIterable<String>(this.typeSuperclassNames);
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
	public Iterable<String> getTypeInterfaceNames() {
		return new LiveCloneIterable<String>(this.typeInterfaceNames);
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
	public ListIterable<String> getTypeTypeArgumentNames() {
		return new LiveCloneListIterable<String>(this.typeTypeArgumentNames);
	}

	public int getTypeTypeArgumentNamesSize() {
		return this.typeTypeArgumentNames.size();
	}

	public String getTypeTypeArgumentName(int index) {
		return this.typeTypeArgumentNames.get(index);
	}

	private void setTypeTypeArgumentNames(List<String> typeTypeArgumentNames) {
		this.synchronizeList(typeTypeArgumentNames, this.typeTypeArgumentNames, TYPE_TYPE_ARGUMENT_NAMES_LIST);
	}
	
	private List<String> buildTypeTypeArgumentNames(String typeSignature, Iterable<ITypeParameter> typeParameters) {
		int arrayDimensionality = convertTypeSignatureToTypeArrayDimensionality(typeSignature);
		return (arrayDimensionality != 0) ? Collections.<String>emptyList() : convertTypeSignatureToTypeTypeArgumentNames(typeSignature, typeParameters);
	}


	// ********** convenience methods **********
	
	private String getTypeSignature() {
		try {
			return this.getAdapter().getTypeSignature();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
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
			JptCommonCorePlugin.log(ex);
			return null;
		}
	}

	private String[] getSuperInterfaceTypeSignatures(IType type) {
		try {
			return type.getSuperInterfaceTypeSignatures();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return StringTools.EMPTY_STRING_ARRAY;
		}
	}

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
			JptCommonCorePlugin.log(ex);
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
			extends BinaryMember.Adapter {
		
		/**
		 * Return the field or getter method's "attribute" name
		 * (e.g. field "foo" -> "foo"; method "getFoo" -> "foo").
		 */
		String getAttributeName();
		
		/**
		 * Return the attribute's type signature.
		 */
		String getTypeSignature() throws JavaModelException;
	}
}
