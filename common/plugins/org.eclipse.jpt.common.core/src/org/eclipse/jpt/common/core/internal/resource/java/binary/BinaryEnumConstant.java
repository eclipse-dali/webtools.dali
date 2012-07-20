/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;

/**
 * Java binary enum constant, IField that returns true to isEnumConstant
 */
final class BinaryEnumConstant
		extends BinaryMember
		implements JavaResourceEnumConstant {
	
	
	
	BinaryEnumConstant(JavaResourceEnum parent, IField enumConstant){
		super(parent, new EnumConstantAdapter(enumConstant));
	}

	@Override
	IField getMember() {
		return (IField) super.getMember();
	}

	// ******** JavaResourceAnnotatedElement implementation ********
	
	public Kind getKind() {
		return Kind.ENUM_CONSTANT;
	}
	
	
	// ******** JavaResourceEnumConstant implementation ********
	
	public String getName() {
		return this.getMember().getElementName();
	}

	public void synchronizeWith(EnumConstantDeclaration astEnumConstant) {
		throw new UnsupportedOperationException();
	}

	public void resolveTypes(EnumConstantDeclaration astEnumConstant) {
		throw new UnsupportedOperationException();
	}

	// ********** IField adapter **********

	static class EnumConstantAdapter 
			implements Adapter {
		
		private final IField enumConstant;
		
		EnumConstantAdapter(IField enumConstant) {
			super();
			this.enumConstant = enumConstant;
		}
		
		public IField getElement() {
			return this.enumConstant;
		}
		
		public Iterable<ITypeParameter> getTypeParameters() {
			try {
				return new ArrayIterable<ITypeParameter>(this.enumConstant.getDeclaringType().getTypeParameters());
			}
			catch (JavaModelException jme) {
				JptCommonCorePlugin.instance().logError(jme);
			}
			return EmptyIterable.instance();
		}
		
		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.enumConstant.getAnnotations();
		}
	}
}
