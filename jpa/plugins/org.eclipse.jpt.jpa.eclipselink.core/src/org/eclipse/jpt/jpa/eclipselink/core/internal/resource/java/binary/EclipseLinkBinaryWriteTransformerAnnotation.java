/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkNullWriteTransformerColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.WriteTransformerAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.WriteTransformer</code>
 */
public class EclipseLinkBinaryWriteTransformerAnnotation
	extends EclipseLinkBinaryTransformerAnnotation
	implements WriteTransformerAnnotation
{
	private ColumnAnnotation column;
	private final ColumnAnnotation nullColumn;


	public EclipseLinkBinaryWriteTransformerAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.column = this.buildColumn();
		this.nullColumn = this.buildNullColumn();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.updateColumn();
	}


	// ********** BinaryTransformerAnnotation implementation **********

	@Override
	String getTransformerClassElementName() {
		return EclipseLink.WRITE_TRANSFORMER__TRANSFORMER_CLASS;
	}

	@Override
	String getMethodElementName() {
		return EclipseLink.WRITE_TRANSFORMER__METHOD;
	}


	// ********** WriteTransformerAnnotation implementation **********

	// ***** column
	public ColumnAnnotation getColumn() {
		return this.column;
	}

	public ColumnAnnotation getNonNullColumn() {
		return (this.column != null) ? this.column : this.nullColumn;
	}

	public ColumnAnnotation addColumn() {
		throw new UnsupportedOperationException();
	}

	public void removeColumn() {
		throw new UnsupportedOperationException();
	}

	public TextRange getColumnTextRange() {
		throw new UnsupportedOperationException();
	}

	private ColumnAnnotation buildColumn() {
		IAnnotation jdtColumn = this.getJdtColumn();
		return (jdtColumn == null) ? null : this.buildColumn(jdtColumn);
	}

	private ColumnAnnotation buildNullColumn() {
		return new EclipseLinkNullWriteTransformerColumnAnnotation(this);
	}

	private ColumnAnnotation buildColumn(IAnnotation jdtColumn) {
		return new BinaryColumnAnnotation(this, jdtColumn);
	}

	private IAnnotation getJdtColumn() {
		return (IAnnotation) this.getJdtMemberValue(EclipseLink.WRITE_TRANSFORMER__COLUMN);
	}

	private void setColumn(ColumnAnnotation column) {
		ColumnAnnotation old = this.column;
		this.column = column;
		this.firePropertyChanged(COLUMN_PROPERTY, old, column);
	}

	// TODO
	private void updateColumn() {
		throw new UnsupportedOperationException();
//		IAnnotation jdtColumn = this.getJdtColumn();
//		if (jdtColumn == null) {
//			this.setColumn(null);
//		} else {
//			if (this.column == null) {
//				this.setColumn(this.buildColumn(jdtColumn));
//			} else {
//				this.column.update(jdtColumn);
//			}
//		}
	}
}
