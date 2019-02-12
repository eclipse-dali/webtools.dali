/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;

/**
 * Java binary enum constant, IField that returns true to isEnumConstant
 */
final class BinaryEnumConstant
		extends BinaryMember
		implements JavaResourceEnumConstant {
	
	BinaryEnumConstant(JavaResourceEnum parent, IField enumConstant){
		this(parent, new EnumConstantAdapter(enumConstant));
	}
	
	private BinaryEnumConstant(JavaResourceEnum parent, EnumConstantAdapter adapter) {
		super(parent, adapter);
		// put initialization here, if any becomes needed
	}
	
	
	@Override
	protected IField getElement() {
		return (IField) super.getElement();
	}
	
	
	// ******** JavaResourceAnnotatedElement implementation ********
	
	public AstNodeType getAstNodeType() {
		return AstNodeType.ENUM_CONSTANT;
	}
	
	
	// ******** JavaResourceEnumConstant implementation ********
	
	public String getName() {
		return getElement().getElementName();
	}
	
	public void synchronizeWith(EnumConstantDeclaration astEnumConstant) {
		throw new UnsupportedOperationException();
	}
	
	public void resolveTypes(EnumConstantDeclaration astEnumConstant) {
		throw new UnsupportedOperationException();
	}
	
	
	// ********** IField adapter **********
	
	static class EnumConstantAdapter 
			implements MemberAdapter {
		
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
