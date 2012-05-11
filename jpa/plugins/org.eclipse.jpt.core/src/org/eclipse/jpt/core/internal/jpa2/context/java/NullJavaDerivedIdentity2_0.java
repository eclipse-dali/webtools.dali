/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.DerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.IdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.MapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.utility.TextRange;

public class NullJavaDerivedIdentity2_0
	extends AbstractJavaJpaContextNode
	implements JavaDerivedIdentity2_0
{
	public NullJavaDerivedIdentity2_0(JavaSingleRelationshipMapping2_0 parent) {
		super(parent);
	}
	
	
	public JavaSingleRelationshipMapping2_0 getMapping() {
		return (JavaSingleRelationshipMapping2_0) getParent();
	}
	
	public void initialize() {
		// no op
	}
	
	public void update() {
		// no op
	}
	
	public DerivedIdentityStrategy2_0 getPredominantDerivedIdentityStrategy() {
		return null;
	}
	
	public MapsIdDerivedIdentityStrategy2_0 getMapsIdDerivedIdentityStrategy() {
		return null;
	}
	
	public boolean usesMapsIdDerivedIdentityStrategy() {
		return false;
	}
	
	public void setMapsIdDerivedIdentityStrategy() {
		// no op
	}
	
	public void unsetMapsIdDerivedIdentityStrategy() {
		// no op
	}
	
	public IdDerivedIdentityStrategy2_0 getIdDerivedIdentityStrategy() {
		return null;
	}
	
	public boolean usesIdDerivedIdentityStrategy() {
		return false;
	}
	
	public void setIdDerivedIdentityStrategy() {
		// no op
	}
	
	public void unsetIdDerivedIdentityStrategy() {
		// no op
	}
	
	public boolean usesNullDerivedIdentityStrategy() {
		return true;
	}
	
	public void setNullDerivedIdentityStrategy() {
		// no op
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}
}
