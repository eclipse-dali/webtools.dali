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

import java.util.Vector;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;

/**
 * binary enum
 */
final class BinaryEnum
		extends BinaryAbstractType
		implements JavaResourceEnum {
	
	private final Vector<JavaResourceEnumConstant> enumConstants;
	
	
	// ********** construction/initialization **********
	
	BinaryEnum(JavaResourceNode parent, IType type) {
		super(parent, type);
		this.enumConstants = this.buildEnumConstants();
	}
	
	
	public Kind getKind() {
		return JavaResourceAnnotatedElement.Kind.ENUM;
	}

	public void synchronizeWith(EnumDeclaration enumDeclaration) {
		throw new UnsupportedOperationException();
	}

	public void resolveTypes(EnumDeclaration enumDeclaration) {
		throw new UnsupportedOperationException();
	}

	// ********** overrides **********
	
	@Override
	public void update() {
		super.update();
		this.updateEnumConstants();
	}
	
	// TODO
	private void updateEnumConstants() {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** enum constants *****
	
	public Iterable<JavaResourceEnumConstant> getEnumConstants() {
		return new LiveCloneIterable<JavaResourceEnumConstant>(this.enumConstants);
	}
	
	private Vector<JavaResourceEnumConstant> buildEnumConstants() {
		Iterable<IField> jdtEnumConstants = this.getEnumConstants(this.getMember());
		Vector<JavaResourceEnumConstant> result = new Vector<JavaResourceEnumConstant>(CollectionTools.size(jdtEnumConstants));
		for (IField jdtEnumConstant : jdtEnumConstants) {
			result.add(this.buildEnumConstant(jdtEnumConstant));
		}
		return result;
	}
	
	private Iterable<IField> getEnumConstants(IType type) {
		return new FilteringIterable<IField>(CollectionTools.iterable(this.getFields(type))) {
			@Override
			protected boolean accept(IField jdtField) {
				return isEnumConstant(jdtField);
			}
		};
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
	
	private boolean isEnumConstant(IField field) {
		try {
			return field.isEnumConstant();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return false;
		}
	}
	
	private JavaResourceEnumConstant buildEnumConstant(IField jdtEnumConstant) {
		return new BinaryEnumConstant(this, jdtEnumConstant);
	}
}
