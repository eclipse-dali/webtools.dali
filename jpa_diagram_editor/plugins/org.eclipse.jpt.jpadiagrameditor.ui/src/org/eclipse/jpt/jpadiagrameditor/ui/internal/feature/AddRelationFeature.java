/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kiril Mitov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import static org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelDir.UNI;
import static org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType.MANY_TO_MANY;
import static org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType.MANY_TO_ONE;
import static org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType.ONE_TO_MANY;
import static org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType.ONE_TO_ONE;

import java.util.List;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IAddBendpointFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.impl.AddBendpointContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorImageCreator;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageCreator;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelDir;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtilImpl;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.Wrp;
import org.eclipse.swt.graphics.Point;


public class AddRelationFeature extends AbstractAddFeature {
	
	private static final double START_COEFFICIENT = 0.1;

	private IJPAEditorImageCreator imageCreator;
	private IJPAEditorUtil jpaEditorUtil; 

    public AddRelationFeature(IFeatureProvider fp) {
		this(fp, new JPAEditorImageCreator(), new JPAEditorUtilImpl());
	}

	public AddRelationFeature(IFeatureProvider fp, 
							  IJPAEditorImageCreator imageCreator, 
							  IJPAEditorUtil jpaEditorUtil) {
        super(fp);
		this.imageCreator = imageCreator;
		this.jpaEditorUtil = jpaEditorUtil;
    }
 
    public PictogramElement add(IAddContext context) {
        final IAddConnectionContext addConContext = (IAddConnectionContext) context;
		final IRelation relation = (IRelation) context.getNewObject();
		final Diagram diagram = getDiagram();
		final Wrp wrp = new Wrp();
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(diagram);
		RecordingCommand rc = new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				FreeFormConnection connection = createConnection(addConContext, relation, diagram);
				imageCreator.createConnectionLine(diagram, connection);
				getFeatureProvider().putKeyToBusinessObject(relation.getId(), relation);
				link(connection, relation);
				layoutPictogramElement(connection);
				wrp.setObj(connection);
			}			
		};
		try {
		ted.getCommandStack().execute(rc);
		} catch (Exception e){
		
		}
		
		return (PictogramElement)wrp.getObj();
    }
        
	private FreeFormConnection createConnection(IAddConnectionContext addConContext, IRelation relation,
			final Diagram diagram) {
        FreeFormConnection connection = getFeatureProvider().getPeServiceUtil().createFreeFormConnection(diagram);
        connection.setStart(addConContext.getSourceAnchor());
        connection.setEnd(addConContext.getTargetAnchor());
        connection.setVisible(true);
        connection.setActive(true);        
        List<Point> points = jpaEditorUtil.createBendPointList(connection, relation.getOwner() == relation.getInverse());
        for (int i = 0; i < points.size(); i++) {
        	Point p = points.get(i);
        	AddBendpointContext ctx = new AddBendpointContext(connection, p.x, p.y, i);
            IAddBendpointFeature ft =getFeatureProvider().getAddBendpointFeature(ctx);
            ft.addBendpoint(ctx);
        }
        addDecorators(connection, relation); 
        addTextDecorators(connection, relation);
		return connection;
	}

	private void addDecorators(FreeFormConnection connection, IRelation relation) {
		RelDir direction = relation.getRelDir();
		RelType type = relation.getRelType();
		if (ONE_TO_ONE.equals(type)) {
			addOneToOneDecorator(connection, direction, relation);
		} else if (ONE_TO_MANY.equals(type) && UNI.equals(direction)) {
			addOneToManyDecorator(connection, relation);
		} else if (MANY_TO_ONE.equals(type)) {
			addManyToOneDecorator(connection, direction, relation);
		} else if (MANY_TO_MANY.equals(type)) {
			addManyToManyDecorator(connection, direction, relation);
		}
	}
	
	private void addTextDecorators(FreeFormConnection connection, IRelation relation) {
		RelDir direction = relation.getRelDir();
		RelType type = relation.getRelType();
		if (ONE_TO_ONE.equals(type)) {
			addOneToOneTextDecorator(connection, direction, relation);
		} else if (ONE_TO_MANY.equals(type) && UNI.equals(direction)) {
			addOneToManyTextDecorator(connection, relation);
		} else if (MANY_TO_ONE.equals(type)) {
			addManyToOneTextDecorator(connection, direction, relation);
		} else if (MANY_TO_MANY.equals(type)) {
			addManyToManyTextDecorator(connection, direction, relation);
		}
	}

	private void addOneToOneDecorator(FreeFormConnection c, RelDir direction, IRelation rel) {
		double startCoefficient = START_COEFFICIENT;
		double endCoefficient = 1.0 - startCoefficient;	
		int len = JPAEditorUtil.calcConnectionLength(c);		
		if (UNI.equals(direction)) {
			ConnectionDecorator d = imageCreator.createArrowConnectionDecorator(c, endCoefficient);
			Graphiti.getGaService().setLocation(d.getGraphicsAlgorithm(),Math.round(-len/10), 0);				
		} 
	}
        
	private void addOneToManyDecorator(FreeFormConnection c, IRelation rel) {
		double startCoefficient = START_COEFFICIENT;
		double endCoefficient = 1.0 - startCoefficient;
		int len = JPAEditorUtil.calcConnectionLength(c);
		ConnectionDecorator d = imageCreator.createManyEndWithArrowDecorator(c, endCoefficient);
		Graphiti.getGaService().setLocation(d.getGraphicsAlgorithm(),Math.round(-len/10), 0);
	}
                
	private void addManyToOneDecorator(FreeFormConnection c, RelDir direction, IRelation rel) {
		double startCoefficient = START_COEFFICIENT;
		double endCoefficient = 1.0 - startCoefficient;
		int len = JPAEditorUtil.calcConnectionLength(c);		
		ConnectionDecorator d1 = imageCreator.createManyStartDecorator(c, startCoefficient);
		Graphiti.getGaService().setLocation(d1.getGraphicsAlgorithm(),Math.round(len/10), 0);
		if (UNI.equals(direction)) {
			ConnectionDecorator d2 = imageCreator.createArrowConnectionDecorator(c, endCoefficient);
			Graphiti.getGaService().setLocation(d2.getGraphicsAlgorithm(),Math.round(-len/10), 0);
		} 
    }
    
	private void addManyToManyDecorator(FreeFormConnection c, RelDir direction, IRelation rel) {
		double startCoefficient = START_COEFFICIENT;
		double endCoefficient = 1.0 - startCoefficient;		
		int len = JPAEditorUtil.calcConnectionLength(c);		
		ConnectionDecorator d1 = imageCreator.createManyStartDecorator(c, startCoefficient);
		Graphiti.getGaService().setLocation(d1.getGraphicsAlgorithm(),Math.round(len/10), 0);
		if (UNI.equals(direction)) {
			ConnectionDecorator d = imageCreator.createManyEndWithArrowDecorator(c, endCoefficient);
			Graphiti.getGaService().setLocation(d.getGraphicsAlgorithm(),Math.round(-len/10), 0);
		} else {
			ConnectionDecorator d = imageCreator.createManyEndDecorator(c, endCoefficient, false);
			Graphiti.getGaService().setLocation(d.getGraphicsAlgorithm(),Math.round(-len/10), 0);
		}
    }

	private void addOneToOneTextDecorator(FreeFormConnection c, RelDir direction, IRelation rel) {
		boolean isOptional = false;
		if (RelDir.BI.equals(direction)) {
			PersistentAttribute inverse = rel.getInverse().getAttributeNamed(rel.getInverseAttributeName());
			AttributeMapping mapping = JpaArtifactFactory.instance().getAttributeMapping(inverse);
			if(mapping instanceof OneToOneMapping){
				isOptional = ((OneToOneMapping)mapping).isOptional();
			}
			imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, isOptional ? JPAEditorConstants.CARDINALITY_ZERO_ONE : JPAEditorConstants.CARDINALITY_ONE, 0.0);				
		}
		PersistentAttribute owner = rel.getOwner().getAttributeNamed(rel.getOwnerAttributeName());
		owner.update();
		if(isDerivedId(owner)){
			isOptional = false;
		} else {
			AttributeMapping mapping = JpaArtifactFactory.instance().getAttributeMapping(owner);
			if(mapping instanceof OneToOneMapping){
				isOptional = ((OneToOneMapping)mapping).isOptional();
			}
		}
		imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, isOptional ? JPAEditorConstants.CARDINALITY_ZERO_ONE : JPAEditorConstants.CARDINALITY_ONE, 1.0);
		imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, rel.getOwnerAttributeName(), 0.0);
		if (!UNI.equals(direction)) {
			imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, rel.getInverseAttributeName(), 1.0);
		}
	}
	
	private boolean isDerivedId(PersistentAttribute attr){
		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(attr);
		if(attributeMapping instanceof SingleRelationshipMapping2_0){
			DerivedIdentity2_0 derivedIdentity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
			if(derivedIdentity.usesIdDerivedIdentityStrategy() || derivedIdentity.usesMapsIdDerivedIdentityStrategy()){
				return true;
			}
		}		
		return false;
	}
        
	private void addOneToManyTextDecorator(FreeFormConnection c, IRelation rel) {
		imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, JPAEditorConstants.CARDINALITY_ZERO_ONE, 0.0);
		imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, JPAEditorConstants.CARDINALITY_ZERO_N, 1.0);	
		imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, rel.getOwnerAttributeName(), 0.0);						
	}
                
	private void addManyToOneTextDecorator(FreeFormConnection c, RelDir direction, IRelation rel) {
		boolean isOptional = false;
		imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, JPAEditorConstants.CARDINALITY_ZERO_N, 0.0);
		imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, rel.getOwnerAttributeName(), 0.0);				

		PersistentAttribute owner = rel.getOwner().getAttributeNamed(rel.getOwnerAttributeName());

		if(isDerivedId(owner)){
			isOptional = false;
		} else {
			AttributeMapping mapping = JpaArtifactFactory.instance().getAttributeMapping(owner);
			if(mapping instanceof ManyToOneMapping) {
				isOptional = ((ManyToOneMapping)mapping).isOptional();
			}
		}
		
		imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, isOptional ?
																	JPAEditorConstants.CARDINALITY_ZERO_ONE :
																	JPAEditorConstants.CARDINALITY_ONE, 1.0);
		if (!UNI.equals(direction)) {
			imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, rel.getInverseAttributeName(), 1.0);
		}
    }
    
	private void addManyToManyTextDecorator(FreeFormConnection c, RelDir direction, IRelation rel) {
		imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, JPAEditorConstants.CARDINALITY_ZERO_N, 0.0);		
		imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, JPAEditorConstants.CARDINALITY_ZERO_N, 1.0);	
		imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, rel.getOwnerAttributeName(), 0.0);
		if (!UNI.equals(direction)) {
			imageCreator.createCardinalityConnectionDecorator(getDiagram(), c, rel.getInverseAttributeName(), 1.0);	
		}
    }
	
    public boolean canAdd(IAddContext context) {
		if (context instanceof IAddConnectionContext && context.getNewObject() instanceof IRelation) {
            return true;
        }
        return false;
    }
    
	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}
    
}

