/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceOneToOneAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OneToOne2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;

/**
 *  SourceOneToOne2_0Annotation
 */
public final class SourceOneToOne2_0Annotation
	extends SourceOneToOneAnnotation
	implements OneToOne2_0Annotation
{
	private static final DeclarationAnnotationElementAdapter<Boolean> ORPHAN_REMOVAL_ADAPTER = buildOrphanRemovalAdapter();
	private final AnnotationElementAdapter<Boolean> orphanRemovalAdapter;
	private Boolean orphanRemoval;

	// ********** constructor **********
	public SourceOneToOne2_0Annotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute);
		this.orphanRemovalAdapter = this.buildBooleanAnnotationElementAdapter(ORPHAN_REMOVAL_ADAPTER);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.orphanRemoval = this.buildOrphanRemoval(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncOrphanRemoval(this.buildOrphanRemoval(astRoot));
	}

	// ********** OneToOne2_0Annotation implementation **********

	public Boolean getOrphanRemoval() {
		return this.orphanRemoval;
	}

	public void setOrphanRemoval(Boolean orphanRemoval) {
		if (this.attributeValueHasChanged(this.orphanRemoval, orphanRemoval)) {
			this.orphanRemoval = orphanRemoval;
			this.orphanRemovalAdapter.setValue(orphanRemoval);
		}
	}

	private void syncOrphanRemoval(Boolean astOrphanRemoval) {
		Boolean old = this.orphanRemoval;
		this.orphanRemoval = astOrphanRemoval;
		this.firePropertyChanged(ORPHAN_REMOVAL_PROPERTY, old, astOrphanRemoval);
	}

	public TextRange getOrphanRemovalTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(ORPHAN_REMOVAL_ADAPTER, astRoot);
	}

	private Boolean buildOrphanRemoval(CompilationUnit astRoot) {
		return this.orphanRemovalAdapter.getValue(astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<Boolean> buildOrphanRemovalAdapter() {
		return buildOrphanRemovalAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.ONE_TO_ONE__ORPHAN_REMOVAL);
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildOrphanRemovalAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(annotationAdapter, elementName, false, BooleanExpressionConverter.instance());
	}


}