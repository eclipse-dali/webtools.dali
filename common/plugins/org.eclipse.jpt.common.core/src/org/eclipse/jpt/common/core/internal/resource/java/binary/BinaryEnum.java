/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;

/**
 * binary enum
 */
final class BinaryEnum
		extends BinaryAbstractType
		implements JavaResourceEnum {
	
	private final Vector<JavaResourceEnumConstant> enumConstants = new Vector<JavaResourceEnumConstant>();
	
	
	// ********** construction/initialization **********
	
	BinaryEnum(JavaResourceNode parent, IType type) {
		this(parent, new TypeAdapter(type));
	}
	
	private BinaryEnum(JavaResourceNode parent, TypeAdapter adapter) {
		super(parent, adapter);
		CollectionTools.addAll(this.enumConstants, buildEnumConstants());
	}
	
	
	public AstNodeType getAstNodeType() {
		return AstNodeType.ENUM;
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
	
	private Iterable<JavaResourceEnumConstant> buildEnumConstants() {
		return IterableTools.transform(getEnumConstants(getElement()), new FieldEnumConstantTransformer());
	}

	/* CU private */ class FieldEnumConstantTransformer
		extends TransformerAdapter<IField, JavaResourceEnumConstant>
	{
		@Override
		public JavaResourceEnumConstant transform(IField field) {
			return BinaryEnum.this.buildEnumConstant(field);
		}
	}
	
	private Iterable<IField> getEnumConstants(IType type) {
		return new FilteringIterable<IField>(IterableTools.iterable(this.getFields(type))) {
			@Override
			protected boolean accept(IField jdtField) {
				return fieldIsEnumConstant(jdtField);
			}
		};
	}
	
	private IField[] getFields(IType type) {
		try {
			return type.getFields();
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return EMPTY_FIELD_ARRAY;
		}
	}
	
	private static final IField[] EMPTY_FIELD_ARRAY = new IField[0];
	
	/* CU private */ boolean fieldIsEnumConstant(IField field) {
		try {
			return field.isEnumConstant();
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}
	
	/* CU private */ JavaResourceEnumConstant buildEnumConstant(IField jdtEnumConstant) {
		return new BinaryEnumConstant(this, jdtEnumConstant);
	}
}
