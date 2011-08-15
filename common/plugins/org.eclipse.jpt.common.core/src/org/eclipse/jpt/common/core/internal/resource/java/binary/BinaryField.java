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

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;

/**
 * binary field
 */
final class BinaryField
	extends BinaryAttribute
	implements JavaResourceField
{

	BinaryField(JavaResourceType parent, IField field) {
		super(parent, new FieldAdapter(field));
	}

	
	// ******** JavaResourceAnnotatedElement implementation ********
	
	public Kind getKind() {
		return Kind.FIELD;
	}

	// ********** adapters **********

	/**
	 * IField adapter
	 */
	static class FieldAdapter
			implements BinaryAttribute.Adapter {
		
		final IField field;
		
		FieldAdapter(IField field) {
			super();
			this.field = field;
		}
		
		public IField getElement() {
			return this.field;
		}
		
		public Iterable<ITypeParameter> getTypeParameters() {
			try {
				return new ArrayIterable<ITypeParameter>(this.field.getDeclaringType().getTypeParameters());
			}
			catch (JavaModelException jme) {
				JptCommonCorePlugin.log(jme);
			}
			return EmptyIterable.instance();
		}
		
		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.field.getAnnotations();
		}
		
		public String getAttributeName() {
			return this.field.getElementName();
		}
		
		public String getTypeSignature() throws JavaModelException {
			return this.field.getTypeSignature();
		}
	}
}
