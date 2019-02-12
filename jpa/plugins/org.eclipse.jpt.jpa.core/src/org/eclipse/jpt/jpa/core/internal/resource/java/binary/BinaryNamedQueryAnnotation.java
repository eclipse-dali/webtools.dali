/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import java.util.List;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;

/**
 * javax.persistence.NamedQuery
 */
public abstract class BinaryNamedQueryAnnotation
	extends BinaryQueryAnnotation
	implements NamedQueryAnnotation
{
	private String query;
	
	public BinaryNamedQueryAnnotation(JavaResourceModel parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.query = this.buildQuery();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setQuery_(this.buildQuery());
	}


	// ********** BinaryNamedQueryAnnotation implementation **********

	@Override
	public String getNameElementName() {
		return JPA.NAMED_QUERY__NAME;
	}

	String getQueryElementName() {
		return JPA.NAMED_QUERY__QUERY;
	}

	@Override
	public String getHintsElementName() {
		return JPA.NAMED_QUERY__HINTS;
	}


	// ********** NamedNativeQueryAnnotation implementation **********

	// ***** query
	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		throw new UnsupportedOperationException();
	}

	private void setQuery_(String query) {
		String old = this.query;
		this.query = query;
		this.firePropertyChanged(QUERY_PROPERTY, old, query);
	}

	private String buildQuery() {
		return (String) this.getJdtMemberValue(this.getQueryElementName());
	}

	public List<TextRange> getQueryTextRanges() {
		throw new UnsupportedOperationException();
	}

}
