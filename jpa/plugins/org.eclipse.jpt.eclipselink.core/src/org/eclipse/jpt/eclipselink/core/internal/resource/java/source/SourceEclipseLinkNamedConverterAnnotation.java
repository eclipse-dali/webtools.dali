/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationElementAdapter;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.OverrideAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;

/**
 * org.eclipse.persistence.annotations.Converter
 * org.eclipse.persistence.annotations.StructConverter
 * org.eclipse.persistence.annotations.TypeConverter
 * org.eclipse.persistence.annotations.ObjectTypeConverter
 */
abstract class SourceEclipseLinkNamedConverterAnnotation
	extends SourceAnnotation<Member>
	implements EclipseLinkNamedConverterAnnotation
{
	final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	final AnnotationElementAdapter<String> nameAdapter;
	String name;
		

	// ********** construction/initialization **********

	SourceEclipseLinkNamedConverterAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
		this.nameDeclarationAdapter = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, this.getNameElementName(), false); // false = do not remove annotation when empty
		this.nameAdapter = new MemberAnnotationElementAdapter<String>(this.member, this.nameDeclarationAdapter);
	}

	public void initialize(CompilationUnit astRoot) {
		this.name = this.buildName(astRoot);
	}
	
	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncName(this.buildName(astRoot));
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
	

	// ********** NamedConverterAnnotation implementation **********

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
		this.firePropertyChanged(OverrideAnnotation.NAME_PROPERTY, old, astName);
	}

	private String buildName(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.nameDeclarationAdapter, pos, astRoot);
	}

	abstract String getNameElementName();

}
