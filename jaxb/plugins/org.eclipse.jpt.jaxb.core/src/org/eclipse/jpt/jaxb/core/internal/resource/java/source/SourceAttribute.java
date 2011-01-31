/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.utility.internal.ClassName;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;

/**
 * Java source attribute (field or method)
 */
abstract class SourceAttribute<A extends Attribute>
	extends SourceMember<A>
	implements JavaResourceAttribute
{
	private int modifiers;

	private String typeName;

	private boolean typeIsInterface;

	private boolean typeIsEnum;

	private final Vector<String> typeSuperclassNames = new Vector<String>();

	private final Vector<String> typeInterfaceNames = new Vector<String>();

	private final Vector<String> typeTypeArgumentNames = new Vector<String>();


	protected SourceAttribute(JavaResourceType parent, A attribute){
		super(parent, attribute);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		ITypeBinding typeBinding = this.getTypeBinding(astRoot); //build once, minor performance tweak for major benefit
		this.modifiers = this.buildModifiers(astRoot);
		this.typeName = this.buildTypeName(typeBinding);
		this.typeIsInterface = this.buildTypeIsInterface(typeBinding);
		this.typeIsEnum = this.buildTypeIsEnum(typeBinding);
		this.typeSuperclassNames.addAll(this.buildTypeSuperclassNames(typeBinding));
		this.typeInterfaceNames.addAll(this.buildTypeInterfaceNames(typeBinding));
		this.typeTypeArgumentNames.addAll(this.buildTypeTypeArgumentNames(typeBinding));
	}


	// ******** overrides ********

	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);
		ITypeBinding typeBinding = this.getTypeBinding(astRoot);//build once, minor performance tweak for major benefit
		this.syncTypeName(this.buildTypeName(typeBinding));
		this.syncTypeSuperclassNames(this.buildTypeSuperclassNames(typeBinding));
		this.syncTypeInterfaceNames(this.buildTypeInterfaceNames(typeBinding));
		this.syncTypeTypeArgumentNames(this.buildTypeTypeArgumentNames(typeBinding));
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		ITypeBinding typeBinding = this.getTypeBinding(astRoot);//build once, minor performance tweak for major benefit
		this.syncModifiers(this.buildModifiers(astRoot));
		this.syncTypeName(this.buildTypeName(typeBinding));
		this.syncTypeIsInterface(this.buildTypeIsInterface(typeBinding));
		this.syncTypeIsEnum(this.buildTypeIsEnum(typeBinding));
		this.syncTypeSuperclassNames(this.buildTypeSuperclassNames(typeBinding));
		this.syncTypeInterfaceNames(this.buildTypeInterfaceNames(typeBinding));
		this.syncTypeTypeArgumentNames(this.buildTypeTypeArgumentNames(typeBinding));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}

	
	// ******** JavaResourcePersistentAttribute implementation ********
	
	public String getName() {
		return this.annotatedElement.getAttributeName();
	}

	public boolean typeIsSubTypeOf(String tn) {
		if (this.typeName == null) {
			return false;
		}
		return this.typeName.equals(tn)
				|| this.typeInterfaceNames.contains(tn)
				|| this.typeSuperclassNames.contains(tn);
	}

	public boolean typeIsVariablePrimitive() {
		return (this.typeName != null) && ClassName.isVariablePrimitive(this.typeName);
	}

	private ITypeBinding getTypeBinding(CompilationUnit astRoot) {
		return this.annotatedElement.getTypeBinding(astRoot);
	}


	// ***** modifiers
	public int getModifiers() {
		return this.modifiers;
	}

	private void syncModifiers(int astModifiers) {
		int old = this.modifiers;
		this.modifiers = astModifiers;
		this.firePropertyChanged(MODIFIERS_PROPERTY, old, astModifiers);
	}

	/**
	 * zero seems like a reasonable default...
	 */
	private int buildModifiers(CompilationUnit astRoot) {
		IBinding binding = this.annotatedElement.getBinding(astRoot);
		return (binding == null) ? 0 : binding.getModifiers();
	}

	// ***** type name
	public String getTypeName() {
		return this.typeName;
	}

	private void syncTypeName(String astTypeName) {
		String old = this.typeName;
		this.typeName = astTypeName;
		this.firePropertyChanged(TYPE_NAME_PROPERTY, old, astTypeName);
	}

	/**
	 * this can be an array (e.g. "java.lang.String[]");
	 * but no generic type arguments
	 */
	private String buildTypeName(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return null;
		}
		// a type variable is what is declared by a generic type;
		// e.g. "E" is a type variable declared by the generic type "Collection" in
		//     public interface Collection<E>
		if (typeBinding.isTypeVariable()) {
			// e.g. "E extends Number" has an erasure of "Number"
			typeBinding = typeBinding.getErasure();
		}
		String tbName = typeBinding.getTypeDeclaration().getQualifiedName();
		return (tbName.length() == 0) ? null : tbName;
	}

	// ***** type is interface
	public boolean typeIsInterface() {
		return this.typeIsInterface;
	}

	private void syncTypeIsInterface(boolean astTypeIsInterface) {
		boolean old = this.typeIsInterface;
		this.typeIsInterface = astTypeIsInterface;
		this.firePropertyChanged(TYPE_IS_INTERFACE_PROPERTY, old, astTypeIsInterface);
	}

	private boolean buildTypeIsInterface(ITypeBinding typeBinding) {
		return (typeBinding != null) && ( ! typeBinding.isArray()) && typeBinding.isInterface();
	}

	// ***** type is enum
	public boolean typeIsEnum() {
		return this.typeIsEnum;
	}

	private void syncTypeIsEnum(boolean astTypeIsEnum) {
		boolean old = this.typeIsEnum;
		this.typeIsEnum = astTypeIsEnum;
		this.firePropertyChanged(TYPE_IS_ENUM_PROPERTY, old, astTypeIsEnum);
	}

	private boolean buildTypeIsEnum(ITypeBinding typeBinding) {
		return (typeBinding != null) && ( ! typeBinding.isArray()) && typeBinding.isEnum();
	}

	// ***** type superclass hierarchy
	public ListIterable<String> getTypeSuperclassNames() {
		return new LiveCloneListIterable<String>(this.typeSuperclassNames);
	}

	private void syncTypeSuperclassNames(List<String> astTypeSuperclassNames) {
		this.synchronizeList(astTypeSuperclassNames, this.typeSuperclassNames, TYPE_SUPERCLASS_NAMES_LIST);
	}

	private List<String> buildTypeSuperclassNames(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return Collections.emptyList();
		}
		ArrayList<String> names = new ArrayList<String>();
		typeBinding = typeBinding.getSuperclass();
		while (typeBinding != null) {
			names.add(typeBinding.getQualifiedName());
			typeBinding = typeBinding.getSuperclass();
		}
		return names;
	}

	// ***** type interface hierarchy
	public Iterable<String> getTypeInterfaceNames() {
		return new LiveCloneIterable<String>(this.typeInterfaceNames);
	}

//	private boolean typeInterfaceNamesContains(String interfaceName) {
//		return this.typeInterfaceNames.contains(interfaceName);
//	}
//
	private void syncTypeInterfaceNames(Collection<String> astTypeInterfaceNames) {
		this.synchronizeCollection(astTypeInterfaceNames, this.typeInterfaceNames, TYPE_INTERFACE_NAMES_COLLECTION);
	}

	private Collection<String> buildTypeInterfaceNames(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return Collections.emptySet();
		}
		HashSet<String> names = new HashSet<String>();
		while (typeBinding != null) {
			this.addInterfaceNamesTo(typeBinding, names);
			typeBinding = typeBinding.getSuperclass();
		}
		return names;
	}

	private void addInterfaceNamesTo(ITypeBinding typeBinding, HashSet<String> names) {
		for (ITypeBinding interfaceBinding : typeBinding.getInterfaces()) {
			names.add(interfaceBinding.getTypeDeclaration().getQualifiedName());
			this.addInterfaceNamesTo(interfaceBinding, names);  // recurse
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

	private void syncTypeTypeArgumentNames(List<String> astTypeTypeArgumentNames) {
		this.synchronizeList(astTypeTypeArgumentNames, this.typeTypeArgumentNames, TYPE_TYPE_ARGUMENT_NAMES_LIST);
	}

	/**
	 * these types can be arrays (e.g. "java.lang.String[]");
	 * but they won't have any further nested generic type arguments
	 * (e.g. "java.util.Collection<java.lang.String>")
	 */
	private List<String> buildTypeTypeArgumentNames(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return Collections.emptyList();
		}

		ITypeBinding[] typeArguments = typeBinding.getTypeArguments();
		if (typeArguments.length == 0) {
			return Collections.emptyList();
		}

		ArrayList<String> names = new ArrayList<String>(typeArguments.length);
		for (ITypeBinding typeArgument : typeArguments) {
			if (typeArgument == null) {
				names.add(null);
			} else {
				// e.g. "? extends Number" has an erasure of "Number"
				ITypeBinding erasure = typeArgument.getErasure();
				names.add(erasure.getTypeDeclaration().getQualifiedName());
			}
		}
		return names;
	}

}
