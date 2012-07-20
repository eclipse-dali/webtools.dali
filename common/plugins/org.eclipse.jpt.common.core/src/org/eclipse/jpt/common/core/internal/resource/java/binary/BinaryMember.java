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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;

/**
 * binary persistent member
 */
abstract class BinaryMember
	extends BinaryAnnotatedElement
	implements JavaResourceMember
{

	private boolean final_;  // 'final' is a reserved word
	private boolean transient_;  // 'transient' is a reserved word
	private boolean public_;  // 'public' is a reserved word
	private boolean static_;  // 'static' is a reserved word
	private boolean protected_; // 'protected' is a reserved word


	// ********** construction/initialization **********

	public BinaryMember(JavaResourceNode parent, Adapter adapter) {
		super(parent, adapter);
		IMember member = adapter.getElement();
		this.final_ = this.buildFinal(member);
		this.transient_ = this.buildTransient(member);
		this.public_ = this.buildPublic(member);
		this.static_ = this.buildStatic(member);
		this.protected_ = this.buildProtected(member);
	}

	// ********** updating **********

	@Override
	public void update() {
		super.update();
		this.update(this.getMember());
	}

	protected void update(IMember member) {
		this.setFinal(this.buildFinal(member));
		this.setTransient(this.buildTransient(member));
		this.setPublic(this.buildPublic(member));
		this.setStatic(this.buildStatic(member));
		this.setProtected(this.buildProtected(member));		
	}

	// ********** simple state **********

	// ***** final
	public boolean isFinal() {
		return this.final_;
	}

	private void setFinal(boolean final_) {
		boolean old = this.final_;
		this.final_ = final_;
		this.firePropertyChanged(FINAL_PROPERTY, old, final_);
	}

	private boolean buildFinal(IMember member) {
		try {
			return Flags.isFinal(member.getFlags());
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	// ***** transient
	public boolean isTransient() {
		return this.transient_;
	}

	private void setTransient(boolean transient_) {
		boolean old = this.transient_;
		this.transient_ = transient_;
		this.firePropertyChanged(TRANSIENT_PROPERTY, old, transient_);
	}

	private boolean buildTransient(IMember member) {
		try {
			return Flags.isTransient(member.getFlags());
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	// ***** public
	public boolean isPublic() {
		return this.public_;
	}

	private void setPublic(boolean public_) {
		boolean old = this.public_;
		this.public_ = public_;
		this.firePropertyChanged(PUBLIC_PROPERTY, old, public_);
	}

	private boolean buildPublic(IMember member) {
		try {
			return Flags.isPublic(member.getFlags());
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	// ***** static
	public boolean isStatic() {
		return this.static_;
	}

	private void setStatic(boolean static_) {
		boolean old = this.static_;
		this.static_ = static_;
		this.firePropertyChanged(STATIC_PROPERTY, old, static_);
	}

	private boolean buildStatic(IMember member) {
		try {
			return Flags.isStatic(member.getFlags());
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	// ***** protected
	public boolean isProtected() {
		return this.protected_;
	}

	private void setProtected(boolean protected_) {
		boolean old = this.protected_;
		this.protected_ = protected_;
		this.firePropertyChanged(PROTECTED_PROPERTY, old, protected_);
	}

	private boolean buildProtected(IMember member) {
		try {
			return Flags.isProtected(member.getFlags());
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	// ********** miscellaneous **********

	IMember getMember() {
		return this.getAdapter().getElement();
	}

	private Adapter getAdapter() {
		return (Adapter) this.adapter;
	}

	/**
	 * Strip off the type signature's parameters if present.
	 * Convert to a readable string.
	 */
	static String convertTypeSignatureToTypeName(String typeSignature) {
		return convertTypeSignatureToTypeName(typeSignature, EmptyIterable.<ITypeParameter>instance());
	}
	
	/**
	 * Strip off the type signature's parameters if present.
	 * Convert to a readable string.
	 */
	static String convertTypeSignatureToTypeName(String typeSignature, Iterable<ITypeParameter> typeParameters) {
		return (typeSignature == null) ? null : convertTypeSignatureToTypeName_(typeSignature, typeParameters);
	}
	
	/**
	 * no null check
	 */
	static String convertTypeSignatureToTypeName_(String typeSignature) {
		return convertTypeSignatureToTypeName(typeSignature, EmptyIterable.<ITypeParameter>instance());
	}
	
	/**
	 * no null check
	 */
	static String convertTypeSignatureToTypeName_(String typeSignature, Iterable<ITypeParameter> typeParameters) {
		String erasureSignature = Signature.getTypeErasure(typeSignature);
		if (Signature.getTypeSignatureKind(erasureSignature) == Signature.TYPE_VARIABLE_SIGNATURE) {
			try {
				String typeParameterName = Signature.toString(erasureSignature);
				for (ITypeParameter typeParameter : typeParameters) {
					if (typeParameterName.equals(typeParameter.getElementName())) {
						String[] bounds = typeParameter.getBoundsSignatures();
						if (bounds.length > 0) {
							return convertTypeSignatureToTypeName_(bounds[0], typeParameters);
						}
					}
				}
			}
			catch (JavaModelException jme) {
				JptCommonCorePlugin.log(jme);
			}
		}
		else if (Signature.getTypeSignatureKind(erasureSignature) == Signature.ARRAY_TYPE_SIGNATURE) {
			int dim = Signature.getArrayCount(erasureSignature);
			String arrayTypeName = convertTypeSignatureToTypeName(Signature.getElementType(erasureSignature), typeParameters);
			return Signature.toString(Signature.createArraySignature(Signature.createTypeSignature(arrayTypeName, true), dim));
		}
		else if (Signature.getTypeSignatureKind(erasureSignature) == Signature.WILDCARD_TYPE_SIGNATURE) {
			// if signature is ? (wildcard) or ? super X (bottom bounded), return top bound, which is Object
			if (String.valueOf(Signature.C_STAR).equals(erasureSignature) || erasureSignature.startsWith(String.valueOf(Signature.C_SUPER))) {
				return Object.class.getName();
			}
			// return top bound
			return Signature.toString(erasureSignature.substring(1));
		}
		return Signature.toString(erasureSignature);
	}
	
	static boolean convertTypeSignatureToTypeIsArray(String typeSignature) {
		return (typeSignature == null) ? false : Signature.getTypeSignatureKind(typeSignature) == Signature.ARRAY_TYPE_SIGNATURE;
	}
	
	static int convertTypeSignatureToTypeArrayDimensionality(String typeSignature) {
		return (typeSignature == null) ? 0 : Signature.getArrayCount(typeSignature);
	}
	
	static String convertTypeSignatureToTypeArrayComponentTypeName(String typeSignature, Iterable<ITypeParameter> typeParameters) {
		return (typeSignature == null) ? null : convertTypeSignatureToTypeName(Signature.getElementType(typeSignature), typeParameters);
	}
	
	/**
	 * these types can be arrays (e.g. "java.lang.String[]");
	 * but they won't have any further nested generic type arguments
	 * (e.g. "java.util.Collection<java.lang.String>")
	 */
	static List<String> convertTypeSignatureToTypeTypeArgumentNames(String typeSignature, Iterable<ITypeParameter> typeParameters) {
		if (typeSignature == null) {
			return Collections.emptyList();
		}
		
		String[] typeArgumentSignatures = Signature.getTypeArguments(typeSignature);
		if (typeArgumentSignatures.length == 0) {
			return Collections.emptyList();
		}
		
		ArrayList<String> names = new ArrayList<String>(typeArgumentSignatures.length);
		for (String typeArgumentSignature : typeArgumentSignatures) {
			names.add(convertTypeSignatureToTypeName(typeArgumentSignature, typeParameters));
		}
		return names;
	}
	
	public boolean isPublicOrProtected() {
		return this.isPublic() || this.isProtected();
	}
	
	// ********** IMember adapter **********

	interface Adapter extends BinaryAnnotatedElement.Adapter {
		/**
		 * Return the adapter's JDT member (IType, IField, IMethod).
		 */
		IMember getElement();
		
		Iterable<ITypeParameter> getTypeParameters();
	}


	// ********** unsupported JavaResourceMember implementation **********

	public Annotation setPrimaryAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames) {
		throw new UnsupportedOperationException();
	}

	public boolean isFor(String memberName, int occurrence) {
		throw new UnsupportedOperationException();
	}
}
