/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.xsd;

import java.util.Stack;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDAttributeGroupDefinition;
import org.eclipse.xsd.XSDAttributeUse;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDModelGroupDefinition;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.util.XSDSwitch;


public class XSDNodeVisitor {
	
	private Stack<XSDComponent> visitedNodeStack = new Stack<XSDComponent>();
	
	
	public void visitNode(XSDComponent node) {
		if (node != null && ! visitedNodeStack.contains(node)) {
			visitedNodeStack.push(node);
			XSDSwitch xsdSwitch = new XSDSwitch() {
				@Override
				public Object caseXSDAttributeDeclaration(XSDAttributeDeclaration object) {
					visitXSDAttributeDeclaration(object);
					return null;
				}
				
				@Override
				public Object caseXSDAttributeGroupDefinition(XSDAttributeGroupDefinition object) {
					visitXSDAttributeGroupDefinition(object);
					return null;
				}
				
				@Override
				public Object caseXSDAttributeUse(XSDAttributeUse object) {
					visitXSDAttributeUse(object);
					return null;
				}
				
				@Override
				public Object caseXSDComplexTypeDefinition(XSDComplexTypeDefinition object) {
					visitXSDComplexTypeDefinition(object);
					return null;
				}
				
				@Override
				public Object caseXSDElementDeclaration(XSDElementDeclaration object) {
					visitXSDElementDeclaration(object);
					return null;
				}
				
				@Override
				public Object caseXSDModelGroup(XSDModelGroup object) {
					visitXSDModelGroup(object);
					return null;
				}
				
				@Override
				public Object caseXSDModelGroupDefinition(XSDModelGroupDefinition object) {
					visitXSDModelGroupDefinition(object);
					return null;
				}
				
				@Override
				public Object caseXSDParticle(XSDParticle object) {
					visitXSDParticle(object);
					return null;
				}
				
				@Override
				public Object caseXSDSimpleTypeDefinition(XSDSimpleTypeDefinition object) {
					visitXSDSimpleTypeDefinition(object);
					return null;
				}
			};
			xsdSwitch.doSwitch(node);
			visitedNodeStack.pop();
		}
	}
	
	public void visitXSDAttributeDeclaration(XSDAttributeDeclaration node) {}
	
	public void visitXSDAttributeGroupDefinition(XSDAttributeGroupDefinition node) {
		for (XSDAttributeUse attrUse : node.getAttributeUses()) {
			visitNode(attrUse);
		}
	}
	
	public void visitXSDAttributeUse(XSDAttributeUse node) {}
	
	public void visitXSDComplexTypeDefinition(XSDComplexTypeDefinition node) {
		if (node.getBaseType() != null) {
			visitNode(node.getBaseType());
		}
		
		for (XSDAttributeUse attrUse : node.getAttributeUses()) {
			visitNode(attrUse);
		}
		
		if (node.getContent() != null) {
			visitNode(node.getContent());
		}
	}
	
	public void visitXSDElementDeclaration(XSDElementDeclaration node) {
		for (XSDElementDeclaration element : node.getSubstitutionGroup()) {
			visitNode(element);
		}
		
		if (node.getTypeDefinition() != null) {
			visitNode(node.getTypeDefinition());
		}
	}
	
	public void visitXSDModelGroup(XSDModelGroup node) {
		for (XSDParticle particle : node.getParticles()) {
			visitNode(particle);
		}
	}
	
	public void visitXSDModelGroupDefinition(XSDModelGroupDefinition node) {
		if (node.getModelGroup() != null) {
			visitNode(node.getModelGroup());
		}
	}
	
	public void visitXSDParticle(XSDParticle node) {
		if (node.getTerm() != null) {
			visitNode(node.getTerm());
		}
	}
	
	public void visitXSDSimpleTypeDefinition(XSDSimpleTypeDefinition node) {
		if (node.getBaseType() != null) {
			visitNode(node.getBaseType());
		}
	}
}
