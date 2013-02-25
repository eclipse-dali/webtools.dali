/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.GeneratorAnnotation;

/**
 * <ul>
 * <li>javax.persistence.SequenceGenerator
 * <li>javax.persistence.TableGenerator
 * </ul>
 */
public abstract class BinaryGeneratorAnnotation
	extends BinaryAnnotation
	implements GeneratorAnnotation
{
	String name;


	protected BinaryGeneratorAnnotation(JavaResourceModel parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** GeneratorAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(this.getNameElementName());
	}

	protected abstract String getNameElementName();

	public TextRange getNameTextRange() {
		throw new UnsupportedOperationException();
	}

}
