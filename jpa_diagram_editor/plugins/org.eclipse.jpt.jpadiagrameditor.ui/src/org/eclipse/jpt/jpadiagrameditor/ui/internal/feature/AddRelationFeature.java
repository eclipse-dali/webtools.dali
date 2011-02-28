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
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;
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
        FreeFormConnection connection = getFeatureProvider().getPeUtil().createFreeFormConnection(diagram);
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
		JpaArtifactFactory.instance().refreshEntityModel(null, rel.getOwner());
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
		JpaArtifactFactory.instance().refreshEntityModel(null, rel.getOwner());
		double startCoefficient = START_COEFFICIENT;
		double endCoefficient = 1.0 - startCoefficient;		
		int len = JPAEditorUtil.calcConnectionLength(c);		
		ConnectionDecorator d1 = imageCreator.createManyStartDecorator(c, startCoefficient);
		Graphiti.getGaService().setLocation(d1.getGraphicsAlgorithm(),Math.round(len/10), 0);
		if (UNI.equals(direction)) {
			ConnectionDecorator d = imageCreator.createManyEndWithArrowDecorator(c, endCoefficient);
			Graphiti.getGaService().setLocation(d.getGraphicsAlgorithm(),Math.round(-len/10), 0);
		} else {
			JpaArtifactFactory.instance().refreshEntityModel(null, rel.getInverse());
			ConnectionDecorator d = imageCreator.createManyEndDecorator(c, endCoefficient);
			Graphiti.getGaService().setLocation(d.getGraphicsAlgorithm(),Math.round(-len/10), 0);
		}
    }
 
	
	
	
	
	
	private void addOneToOneTextDecorator(FreeFormConnection c, RelDir direction, IRelation rel) {
		JpaArtifactFactory.instance().refreshEntityModel(null, rel.getOwner());
		OneToOneAnnotation a = null;
		Boolean optional;
		boolean isOptional = false;
		if (RelDir.UNI.equals(direction)) {
			isOptional = true;
		} else {
			JavaPersistentAttribute inverse = rel.getInverse().getAttributeNamed(rel.getInverseAttributeName());
			JpaArtifactFactory.instance().refreshEntityModel(null, (JavaPersistentType)inverse.getParent());
			JavaAttributeMapping mapping = inverse.getMapping();
			a = (OneToOneAnnotation)mapping.getMappingAnnotation();
			if (a != null) {
				optional = a.getOptional();
				isOptional = (optional == null) ? true : optional.booleanValue();
				imageCreator.createCardinalityConnectionDecorator(c, isOptional ? JPAEditorConstants.CARDINALITY_ZERO_ONE : JPAEditorConstants.CARDINALITY_ONE, 0.0);				
			}
		}
		JpaArtifactFactory.instance().refreshEntityModel(null, rel.getOwner());
		JavaPersistentAttribute owner = rel.getOwner().getAttributeNamed(rel.getOwnerAttributeName());
		owner.update();
		JavaAttributeMapping mapping = owner.getMapping();  
		a = (OneToOneAnnotation)mapping.getMappingAnnotation();		
		if (a == null)
			return;
		optional = a.getOptional();
		isOptional = (optional == null) ? true : optional.booleanValue();		
		imageCreator.createCardinalityConnectionDecorator(c, isOptional ? JPAEditorConstants.CARDINALITY_ZERO_ONE : JPAEditorConstants.CARDINALITY_ONE, 1.0);									 
		imageCreator.createCardinalityConnectionDecorator(c, rel.getOwnerAttributeName(), 0.0);				
		if (!UNI.equals(direction)) {
			JpaArtifactFactory.instance().refreshEntityModel(null, rel.getInverse());
			imageCreator.createCardinalityConnectionDecorator(c, rel.getInverseAttributeName(), 1.0);				
		}
	}
        
	private void addOneToManyTextDecorator(FreeFormConnection c, IRelation rel) {
		JpaArtifactFactory.instance().refreshEntityModel(null, rel.getOwner());
		imageCreator.createCardinalityConnectionDecorator(c, JPAEditorConstants.CARDINALITY_ZERO_ONE, 0.0);
		imageCreator.createCardinalityConnectionDecorator(c, JPAEditorConstants.CARDINALITY_ZERO_N, 1.0);	
		imageCreator.createCardinalityConnectionDecorator(c, rel.getOwnerAttributeName(), 0.0);						
	}
                
	private void addManyToOneTextDecorator(FreeFormConnection c, RelDir direction, IRelation rel) {
		JpaArtifactFactory.instance().refreshEntityModel(null, rel.getOwner());
		imageCreator.createCardinalityConnectionDecorator(c, JPAEditorConstants.CARDINALITY_ZERO_N, 0.0);
		imageCreator.createCardinalityConnectionDecorator(c, rel.getOwnerAttributeName(), 0.0);				
		JavaPersistentAttribute owner = rel.getOwner().getAttributeNamed(rel.getOwnerAttributeName());
		JavaAttributeMapping mapping = owner.getMapping();
		ManyToOneAnnotation a = (ManyToOneAnnotation)mapping.getMappingAnnotation();
		if (a == null) 
			return;
		Boolean optional = a.getOptional();
		boolean isOptional = (optional == null) ? true : optional.booleanValue();		
		imageCreator.createCardinalityConnectionDecorator(c, isOptional ?
																	JPAEditorConstants.CARDINALITY_ZERO_ONE :
																	JPAEditorConstants.CARDINALITY_ONE, 1.0);
		if (!UNI.equals(direction)) {
			JpaArtifactFactory.instance().refreshEntityModel(null, rel.getInverse());
			imageCreator.createCardinalityConnectionDecorator(c, rel.getInverseAttributeName(), 1.0);
		}
    }
    
	private void addManyToManyTextDecorator(FreeFormConnection c, RelDir direction, IRelation rel) {
		JpaArtifactFactory.instance().refreshEntityModel(null, rel.getOwner());
		imageCreator.createCardinalityConnectionDecorator(c, JPAEditorConstants.CARDINALITY_ZERO_N, 0.0);		
		imageCreator.createCardinalityConnectionDecorator(c, JPAEditorConstants.CARDINALITY_ZERO_N, 1.0);	
		imageCreator.createCardinalityConnectionDecorator(c, rel.getOwnerAttributeName(), 0.0);
		if (!UNI.equals(direction)) {
			JpaArtifactFactory.instance().refreshEntityModel(null, rel.getInverse());
			imageCreator.createCardinalityConnectionDecorator(c, rel.getInverseAttributeName(), 1.0);	
		}
    }

	
	
	
	
	
    public boolean canAdd(IAddContext context) {
		if (context instanceof IAddConnectionContext && context.getNewObject() instanceof IRelation) {
            return true;
        }
        return false;
    }
    
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}
    
}

