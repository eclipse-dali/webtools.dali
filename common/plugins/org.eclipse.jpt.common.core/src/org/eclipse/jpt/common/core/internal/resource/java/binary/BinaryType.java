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

import java.util.Collection;
import java.util.Vector;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.MethodSignature;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;

/**
 * binary type
 */
final class BinaryType
	extends BinaryAbstractType
	implements JavaResourceType
{
	private String superclassQualifiedName;

	private boolean abstract_;  // 'abstract' is a reserved word

	private boolean hasNoArgConstructor;

	private boolean hasPrivateNoArgConstructor;

	private final Vector<JavaResourceField> fields;

	private final Vector<JavaResourceMethod> methods;


	// ********** construction/initialization **********

	BinaryType(JavaResourceNode parent, IType type) {
		super(parent, type);
		this.superclassQualifiedName = this.buildSuperclassQualifiedName();
		this.abstract_ = this.buildAbstract();
		this.hasNoArgConstructor = this.buildHasNoArgConstructor();
		this.hasPrivateNoArgConstructor = this.buildHasPrivateNoArgConstructor();
		this.fields = this.buildFields();
		this.methods = this.buildMethods();
	}

	public Kind getKind() {
		return JavaResourceAnnotatedElement.Kind.TYPE;
	}


	// ********** overrides **********

	@Override
	public void update() {
		super.update();
		this.setSuperclassQualifiedName(this.buildSuperclassQualifiedName());
		this.setAbstract(this.buildAbstract());
		this.setHasNoArgConstructor(this.buildHasNoArgConstructor());
		this.setHasPrivateNoArgConstructor(this.buildHasPrivateNoArgConstructor());
		this.updateFields();
		this.updateMethods();
	}

	// TODO
	private void updateFields() {
		throw new UnsupportedOperationException();
	}

	// TODO
	private void updateMethods() {
		throw new UnsupportedOperationException();
	}

	
	
	// ********** JavaResourceType implementation **********

	// ***** superclass qualified name
	public String getSuperclassQualifiedName() {
		return this.superclassQualifiedName;
	}

	private void setSuperclassQualifiedName(String superclassQualifiedName) {
		String old = this.superclassQualifiedName;
		this.superclassQualifiedName = superclassQualifiedName;
		this.firePropertyChanged(SUPERCLASS_QUALIFIED_NAME_PROPERTY, old, superclassQualifiedName);
	}

	private String buildSuperclassQualifiedName() {
		return convertTypeSignatureToTypeName(this.getSuperclassTypeSignature());
	}

	private String getSuperclassTypeSignature() {
		try {
			return this.getMember().getSuperclassTypeSignature();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return null;
		}
	}


	// ***** abstract
	public boolean isAbstract() {
		return this.abstract_;
	}

	private void setAbstract(boolean abstract_) {
		boolean old = this.abstract_;
		this.abstract_ = abstract_;
		this.firePropertyChanged(ABSTRACT_PROPERTY, old, abstract_);
	}

	private boolean buildAbstract() {
		try {
			return Flags.isAbstract(this.getMember().getFlags());
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}

	// ***** no-arg constructor
	public boolean hasNoArgConstructor() {
		return this.hasNoArgConstructor;
	}

	private void setHasNoArgConstructor(boolean hasNoArgConstructor) {
		boolean old = this.hasNoArgConstructor;
		this.hasNoArgConstructor = hasNoArgConstructor;
		this.firePropertyChanged(NO_ARG_CONSTRUCTOR_PROPERTY, old, hasNoArgConstructor);
	}

	private boolean buildHasNoArgConstructor() {
		return this.findNoArgConstructor() != null;
	}

	private IMethod findNoArgConstructor() {
		try {
			for (IMethod method : this.getMember().getMethods()) {
				if (method.isConstructor()) {
					return method;
				}
			}
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
		}
		return null;
	}

	// ***** private no-arg constructor
	public boolean hasPrivateNoArgConstructor() {
		return this.hasPrivateNoArgConstructor;
	}

	private void setHasPrivateNoArgConstructor(boolean hasPrivateNoArgConstructor) {
		boolean old = this.hasPrivateNoArgConstructor;
		this.hasPrivateNoArgConstructor = hasPrivateNoArgConstructor;
		this.firePropertyChanged(PRIVATE_NO_ARG_CONSTRUCTOR_PROPERTY, old, hasPrivateNoArgConstructor);
	}

	private boolean buildHasPrivateNoArgConstructor() {
		IMethod method = this.findNoArgConstructor();
		try {
			return method != null && Flags.isPrivate(method.getFlags());
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}


	// ********** misc **********

	public boolean hasAnyAnnotatedFields() {
		for (JavaResourceField field : this.getFields()) {
			if (field.isAnnotated()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasAnyAnnotatedMethods() {
		for (JavaResourceMethod method : this.getMethods()) {
			if (method.isAnnotated()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IType getMember() {
		return super.getMember();
	}


	// ********** fields **********

	public Iterable<JavaResourceField> getFields() {
		return new LiveCloneIterable<JavaResourceField>(this.fields);
	}
	
	private void addField(JavaResourceField field) {
		this.addItemToCollection(field, this.fields, FIELDS_COLLECTION);
	}

	private JavaResourceField getField(String fieldName, int occurrence) {
		for (JavaResourceField field : this.getFields()) {
			if (field.isFor(fieldName, occurrence)) {
				return field;
			}
		}
		return null;
	}

	private void removeFields(Collection<JavaResourceField> remove) {
		this.removeItemsFromCollection(remove, this.fields, FIELDS_COLLECTION);
	}

	private Vector<JavaResourceField> buildFields() {
		IField[] jdtFields = this.getFields(this.getMember());
		Vector<JavaResourceField> result = new Vector<JavaResourceField>(jdtFields.length);
		for (IField jdtField : jdtFields) {
			result.add(this.buildField(jdtField));
		}
		return result;
	}

	private IField[] getFields(IType type) {
		try {
			return type.getFields();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return EMPTY_FIELD_ARRAY;
		}
	}
	private static final IField[] EMPTY_FIELD_ARRAY = new IField[0];

	private JavaResourceField buildField(IField jdtField) {
		return new BinaryField(this, jdtField);
	}


	// ********** methods **********

	public Iterable<JavaResourceMethod> getMethods() {
		return new LiveCloneIterable<JavaResourceMethod>(this.methods);
	}

	private JavaResourceMethod getMethod(MethodSignature signature, int occurrence) {
		for (JavaResourceMethod method : this.getMethods()) {
			if (method.isFor(signature, occurrence)) {
				return method;
			}
		}
		return null;
	}

	private void addMethod(JavaResourceMethod method) {
		this.addItemToCollection(method, this.methods, METHODS_COLLECTION);
	}

	private void removeMethods(Collection<JavaResourceMethod> remove) {
		this.removeItemsFromCollection(remove, this.methods, METHODS_COLLECTION);
	}

	private Vector<JavaResourceMethod> buildMethods() {
		IMethod[] jdtMethods = this.getMethods(this.getMember());
		Vector<JavaResourceMethod> result = new Vector<JavaResourceMethod>(jdtMethods.length);
		for (IMethod jdtMethod : jdtMethods) {
			result.add(this.buildMethod(jdtMethod));
		}
		return result;
	}

	private IMethod[] getMethods(IType type) {
		try {
			return type.getMethods();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return EMPTY_METHOD_ARRAY;
		}
	}
	private static final IMethod[] EMPTY_METHOD_ARRAY = new IMethod[0];

	private JavaResourceMethod buildMethod(IMethod jdtMethod) {
		return new BinaryMethod(this, jdtMethod);
	}

}
