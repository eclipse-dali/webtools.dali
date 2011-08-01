/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;

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
	
	
	// ********** IField adapter **********

	static class EnumConstantAdapter implements Adapter {
		private final IField enumConstant;

		EnumConstantAdapter(IField enumConstant) {
			super();
			this.enumConstant = enumConstant;
		}

		public IField getElement() {
			return this.enumConstant;
		}

		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.enumConstant.getAnnotations();
		}
	}
}
