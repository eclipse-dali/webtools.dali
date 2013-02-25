/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.resource.java.QueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;

/**
 * <ul>
 * <li><code>javax.persistence.NamedQuery</code>
 * <li><code>javax.persistence.NamedNativeQuery</code>
 * <li><code>javax.persistence.NamedStoredProcedureQuery</code>
 * </ul>
 */
public abstract class SourceQueryAnnotation
	extends SourceAnnotation
	implements QueryAnnotation
{
	DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	AnnotationElementAdapter<String> nameAdapter;
	String name;
	TextRange nameTextRange;

	final QueryHintsAnnotationContainer hintsContainer = new QueryHintsAnnotationContainer();


	protected SourceQueryAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();

	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.name = this.buildName(astAnnotation);
		this.nameTextRange = this.buildNameTextRange(astAnnotation);
		this.hintsContainer.initializeFromContainerAnnotation(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncName(this.buildName(astAnnotation));
		this.nameTextRange = this.buildNameTextRange(astAnnotation);
		this.hintsContainer.synchronize(astAnnotation);
	}


	// ********** BaseNamedQueryAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.attributeValueHasChanged(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(Annotation astAnnotation) {
		return this.nameAdapter.getValue(astAnnotation);
	}

	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}

	private TextRange buildNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, this.getNameElementName());
	}

	private AnnotationElementAdapter<String> buildNameAdapter() {
		return this.buildStringElementAdapter(this.nameDeclarationAdapter);
	}

	protected abstract String getNameElementName();


	// ***** hints

	public ListIterable<QueryHintAnnotation> getHints() {
		return this.hintsContainer.getNestedAnnotations();
	}

	public int getHintsSize() {
		return this.hintsContainer.getNestedAnnotationsSize();
	}

	public QueryHintAnnotation hintAt(int index) {
		return this.hintsContainer.getNestedAnnotation(index);
	}

	public QueryHintAnnotation addHint(int index) {
		return this.hintsContainer.addNestedAnnotation(index);
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		this.hintsContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}

	public void removeHint(int index) {
		this.hintsContainer.removeNestedAnnotation(index);
	}

	protected abstract QueryHintAnnotation buildHint(int index);

	protected abstract String getHintsElementName();


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.name == null) &&
				this.hintsContainer.isEmpty();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** hint container **********
	/**
	 * adapt the AnnotationContainer interface to the xml schema's xmlns
	 */
	class QueryHintsAnnotationContainer
		extends AnnotationContainer<QueryHintAnnotation>
	{
		@Override
		protected String getNestedAnnotationsListName() {
			return HINTS_LIST;
		}
		@Override
		protected String getElementName() {
			return SourceQueryAnnotation.this.getHintsElementName();
		}
		@Override
		protected String getNestedAnnotationName() {
			return QueryHintAnnotation.ANNOTATION_NAME;
		}
		@Override
		protected QueryHintAnnotation buildNestedAnnotation(int index) {
			return SourceQueryAnnotation.this.buildHint(index);
		}
	}
}
