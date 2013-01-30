/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;


public class JavaResourceTypeBinding
		implements TypeBinding {
	
	private String key;
	
	private String qualifiedName;
	
	private String simpleName;
	
	private String packageName;
	
	private boolean isInterface;
	
	private boolean isEnum;
	
	private boolean isGenericTypeDeclaration;
	
	private boolean isMemberTypeDeclaration;
	
	private final Vector<String> superclassNames = new Vector<String>();

	private final Vector<String> interfaceNames = new Vector<String>();
	
	private int arrayDimensionality;
	
	private String arrayComponentTypeName;
	
	private final Vector<String> typeArgumentNames = new Vector<String>();
	
	
	public JavaResourceTypeBinding(ITypeBinding jdtTypeBinding) {
		this.key = jdtTypeBinding == null ? null : jdtTypeBinding.getKey();
		this.qualifiedName = buildQualifiedName(jdtTypeBinding);
		this.simpleName = buildSimpleName(jdtTypeBinding);
		this.packageName = buildPackageName(jdtTypeBinding);
		this.isInterface = buildIsInterface(jdtTypeBinding);
		this.isEnum = buildIsEnum(jdtTypeBinding);
		this.isGenericTypeDeclaration = buildIsGenericTypeDeclaration(jdtTypeBinding);
		this.isMemberTypeDeclaration = buildIsMemberTypeDeclaration(jdtTypeBinding);
		this.arrayDimensionality = buildArrayDimensionality(jdtTypeBinding);
		this.arrayComponentTypeName = buildArrayComponentTypeName(jdtTypeBinding);
		this.superclassNames.addAll(buildSuperclassNames(jdtTypeBinding));
		this.interfaceNames.addAll(buildInterfaceNames(jdtTypeBinding));
		this.typeArgumentNames.addAll(buildTypeArgumentNames(jdtTypeBinding));		
	}
	
	
	public boolean isEquivalentTo(ITypeBinding typeBinding) {
		if (this.key == null) {
			return typeBinding == null; 
		}
		if (typeBinding == null) {
			return false;
		}
		return this.key.equals(typeBinding.getKey());
	}
	
	public String getQualifiedName() {
		return this.qualifiedName;
	}
	
	/**
	 * this can be an array (e.g. "java.lang.String[]");
	 * but no generic type arguments
	 */
	private String buildQualifiedName(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return null;
		}
		
		typeBinding = typeBinding.getErasure();
		String tbName = typeBinding.getTypeDeclaration().getQualifiedName();
		return (tbName.length() == 0) ? null : tbName;
	}
	
	public String getSimpleName() {
		return this.simpleName;
	}
	
	private String buildSimpleName(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return null;
		}
		
		typeBinding = typeBinding.getErasure();
		String tbName = typeBinding.getTypeDeclaration().getName();
		return (tbName.length() == 0) ? null : tbName;
	}
	
	public String getPackageName() {
		return this.packageName;
	}
	
	private String buildPackageName(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return null;
		}
		
		typeBinding = typeBinding.getErasure();
		IPackageBinding pkg = typeBinding.getTypeDeclaration().getPackage();
		return (pkg == null) ? null : pkg.getName();
	}
	
	public boolean isInterface() {
		return this.isInterface;
	}
	
	private boolean buildIsInterface(ITypeBinding typeBinding) {
		return (typeBinding != null) && ( ! typeBinding.isArray()) && typeBinding.isInterface();
	}
	
	public boolean isEnum() {
		return this.isEnum;
	}
	
	private boolean buildIsEnum(ITypeBinding typeBinding) {
		return (typeBinding != null) && ( ! typeBinding.isArray()) && typeBinding.isEnum();
	}
	
	public boolean isVariablePrimitive() {
		return (this.qualifiedName != null) && ClassNameTools.isVariablePrimitive(this.qualifiedName);
	}
	
	public boolean isGenericTypeDeclaration() {
		return this.isGenericTypeDeclaration;
	}
	
	private boolean buildIsGenericTypeDeclaration(ITypeBinding typeBinding) {
		return typeBinding != null && typeBinding.isGenericType();
	}
	
	public boolean isMemberTypeDeclaration() {
		return this.isMemberTypeDeclaration;
	}
	
	private boolean buildIsMemberTypeDeclaration(ITypeBinding typeBinding) {
		return (typeBinding == null) ? false : typeBinding.isMember();
	}
	
	private List<String> buildSuperclassNames(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return Collections.emptyList();
		}
		ArrayList<String> names = new ArrayList<String>();
		typeBinding = typeBinding.getSuperclass();
		while (typeBinding != null) {
			names.add(typeBinding.getErasure().getQualifiedName());
			typeBinding = typeBinding.getSuperclass();
		}
		return names;
	}
	
	private Collection<String> buildInterfaceNames(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return Collections.emptySet();
		}
		HashSet<String> names = new HashSet<String>();
		while (typeBinding != null) {
			addInterfaceNamesTo(typeBinding, names);
			typeBinding = typeBinding.getSuperclass();
		}
		return names;
	}
	
	private void addInterfaceNamesTo(ITypeBinding typeBinding, HashSet<String> names) {
		for (ITypeBinding interfaceBinding : typeBinding.getInterfaces()) {
			names.add(interfaceBinding.getTypeDeclaration().getErasure().getQualifiedName());
			addInterfaceNamesTo(interfaceBinding, names);  // recurse
		}
	}
	
	public boolean isSubTypeOf(String tn) {
		if (this.qualifiedName == null) {
			return false;
		}
		return this.qualifiedName.equals(tn)
				|| this.interfaceNames.contains(tn)
				|| this.superclassNames.contains(tn);
	}
	
	public int getArrayDimensionality() {
		return this.arrayDimensionality;
	}
	
	private int buildArrayDimensionality(ITypeBinding typeBinding) {
		return (typeBinding == null) ? 0 : typeBinding.getDimensions();
	}
	
	public boolean isArray() {
		return this.arrayDimensionality > 0;
	}
	
	public String getArrayComponentTypeName() {
		return this.arrayComponentTypeName;
	}
	
	private String buildArrayComponentTypeName(ITypeBinding typeBinding) {
		if (typeBinding == null || ! typeBinding.isArray()) {
			return null;
		}
		
		// the component type of String[][] is actually String[], whereas we want String
		while (typeBinding.isArray()) {
			typeBinding = typeBinding.getComponentType();
			
			if (typeBinding == null) {
				return null;
			}
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
	
	public ListIterable<String> getTypeArgumentNames() {
		return IterableTools.cloneLive(this.typeArgumentNames);
	}

	public int getTypeArgumentNamesSize() {
		return this.typeArgumentNames.size();
	}

	public String getTypeArgumentName(int index) {
		return this.typeArgumentNames.get(index);
	}
	
	/**
	 * these types can be arrays (e.g. "java.lang.String[]");
	 * but they won't have any further nested generic type arguments
	 * (e.g. "java.util.Collection<java.lang.String>")
	 */
	private List<String> buildTypeArgumentNames(ITypeBinding typeBinding) {
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
