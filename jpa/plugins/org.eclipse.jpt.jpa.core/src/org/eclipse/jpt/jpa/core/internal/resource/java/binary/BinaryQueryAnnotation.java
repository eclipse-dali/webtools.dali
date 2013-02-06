/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.resource.java.QueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;

/**
 * javax.persistence.NamedQuery
 * javax.persistence.NamedNativeQuery
 * javax.persistence.NamedStoredProcedureQuery
 */
public abstract class BinaryQueryAnnotation
	extends BinaryAnnotation
	implements QueryAnnotation
{
	String name;
	final Vector<QueryHintAnnotation> hints;


	protected BinaryQueryAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.hints = this.buildHints();
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
		this.updateHints();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** BaseNamedQueryAnnotation implementation **********

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

	public abstract String getNameElementName();

	public TextRange getNameTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** hints

	public ListIterable<QueryHintAnnotation> getHints() {
		return IterableTools.cloneLive(this.hints);
	}

	public int getHintsSize() {
		return this.hints.size();
	}

	public QueryHintAnnotation hintAt(int index) {
		return this.hints.get(index);
	}

	public QueryHintAnnotation addHint(int index) {
		throw new UnsupportedOperationException();
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeHint(int index) {
		throw new UnsupportedOperationException();
	}

	private Vector<QueryHintAnnotation> buildHints() {
		Object[] jdtHints = this.getJdtMemberValues(this.getHintsElementName());
		Vector<QueryHintAnnotation> result = new Vector<QueryHintAnnotation>(jdtHints.length);
		for (Object jdtHint : jdtHints) {
			result.add(new BinaryQueryHintAnnotation(this, (IAnnotation) jdtHint));
		}
		return result;
	}

	public abstract String getHintsElementName();

	// TODO
	private void updateHints() {
		throw new UnsupportedOperationException();
	}

}
