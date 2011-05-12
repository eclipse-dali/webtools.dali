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
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveConnectionDecoratorFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveConnectionDecoratorContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.internal.services.GraphitiInternal;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.core.SourceType;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddAllEntitiesFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddJPAEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ClickAddAttributeButtonFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ClickRemoveAttributeButtonFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CollapseAllEntitiesFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CollapseCompartmentShapeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CollapseEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateJPAEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateJPAEntityFromMappedSuperclassFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateManyToManyBiDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateManyToManyUniDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateManyToOneBiDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateManyToOneUniDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateOneToManyUniDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateOneToOneBiDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateOneToOneUniDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.DeleteJPAEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.DeleteRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.DirectEditAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.DirectEditJPAEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ExpandCompartmentShapeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ExpandEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.JPAMoveConnectionDecoratorFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.LayoutJPAEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.MoveAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.MoveEntityShapeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RemoveAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RemoveJPAEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RemoveRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ResizeAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ResizeJPAEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.IModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtilImpl;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.AbstractRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelDir;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdaterImpl;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IGraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJpaSolver;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IPeServiceUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtilImpl;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPASolver;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.PeServiceUtilImpl;
import org.eclipse.ui.PlatformUI;


@SuppressWarnings("restriction")
public class JPAEditorFeatureProvider extends DefaultFeatureProvider implements IJPAEditorFeatureProvider {
	
	private ClickAddAttributeButtonFeature clickAddAttBtnFeat = null;
	private ClickRemoveAttributeButtonFeature clickRemoveAttBtnFeat = null;
	private IPeServiceUtil peUtil = new PeServiceUtilImpl();
	private IJPAEditorUtil jpaEditorUtil = new JPAEditorUtilImpl();
	private IModelIntegrationUtil moinIntegrationUtil = new ModelIntegrationUtilImpl(); 
	private IGraphicsUpdater graphicsUpdater = new GraphicsUpdaterImpl(); 
	
    public JPAEditorFeatureProvider(IDiagramTypeProvider dtp, IJpaSolver is) {
        super(dtp);
        is.setFeatureProvider(this);
        this.setIndependenceSolver(is);
    }
    
    public void stopThread() {
    	if (getIndependenceSolver() != null)
    		((JPASolver)getIndependenceSolver()).stopThread();
    }
    
	public void addJPTForUpdate(String jptName) {
		((JPASolver)getIndependenceSolver()).addJPTForUpdate(jptName);
	}

    	
	public void addRemoveIgnore(JavaPersistentType jpt, String atName) {
		((JPASolver)getIndependenceSolver()).addRemoveIgnore(jpt.getName() + "." + atName);  //$NON-NLS-1$
	}
	
	public void addAddIgnore(JavaPersistentType jpt, String atName) {
		((JPASolver)getIndependenceSolver()).addAddIgnore(jpt.getName() + "." + atName); //$NON-NLS-1$
	}
	
	public HashSet<String> getAddIgnore() {
		return ((JPASolver)getIndependenceSolver()).getAddIgnore(); 
	}

	
	public void addAttribForUpdate(PersistenceUnit pu, String entAtMappedBy) {
		((JPASolver)getIndependenceSolver()).addAttribForUpdate(pu, entAtMappedBy);
	}

	public boolean existsRelation(JavaPersistentType jpt1, JavaPersistentType jpt2) {
		return ((JPASolver)getIndependenceSolver()).existsRelation(jpt1, jpt2);
	}
    
    @Override
    public IAddFeature getAddFeature(IAddContext context) {
    	Object newObj = context.getNewObject(); 
        if (newObj instanceof JavaPersistentType) {
            return new AddJPAEntityFeature(this);
        } else if (newObj instanceof AbstractRelation) {
        	 return new AddRelationFeature(this);
        } else if (newObj instanceof PersistentAttribute) { 
        	if (Diagram.class.isInstance(context.getTargetContainer())) {     			
        		return null;
        	} else {
        		return new AddAttributeFeature(this);
        	}
        } else if (newObj instanceof CompilationUnit) {
        	CompilationUnit cu = (CompilationUnit)newObj;
        	JavaPersistentType jpt = JPAEditorUtil.getJPType(cu);
        	if (jpt != null) return new AddJPAEntityFeature(this);
        } else if ((newObj instanceof JpaNode)) {
        	return new AddAllEntitiesFeature(this);
        } else if (newObj instanceof SourceType) {
        	return new AddJPAEntityFeature(this);
        }
        return super.getAddFeature(context);
    }
    
    
    @Override
    public ICreateFeature[] getCreateFeatures() {
    	return new ICreateFeature[] { new CreateJPAEntityFeature(this), new CreateJPAEntityFromMappedSuperclassFeature(this) };
    }
    
    @Override
	public IDeleteFeature getDeleteFeature(IDeleteContext context) {
    	PictogramElement pe = context.getPictogramElement();
    	Object bo = getBusinessObjectForPictogramElement(pe);
    	if (bo instanceof JavaPersistentType) {
    		return new DeleteJPAEntityFeature(this);	
    	} else if (bo instanceof AbstractRelation) {
    		return new DeleteRelationFeature(this);
    	} else if (bo instanceof JavaPersistentAttribute) {
    		return new ClickRemoveAttributeButtonFeature(this);
    	}
		return null;
	}    

	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		ICustomFeature[] ret = super.getCustomFeatures(context);
		List<ICustomFeature> retList = new ArrayList<ICustomFeature>();
		for (int i = 0; i < ret.length; i++) {
			retList.add(ret[i]);
		}
		
		retList.add(new CollapseAllEntitiesFeature(this));

		retList.add(new CollapseEntityFeature(this));
		retList.add(new ExpandEntityFeature(this));
	
		retList.add(new CollapseCompartmentShapeFeature(this));
		retList.add(new ExpandCompartmentShapeFeature(this));
		
		ret = retList.toArray(ret);
		return ret;
	}
    
    
    @Override
    public IResizeShapeFeature getResizeShapeFeature(IResizeShapeContext context) {
		PictogramElement pe = context.getPictogramElement();
		if (getBusinessObjectForPictogramElement(pe) instanceof JavaPersistentType) {
			return new ResizeJPAEntityFeature(this);
		}
    	GraphicsAlgorithm ga = pe.getGraphicsAlgorithm();
    	if (ga == null) 
    		return super.getResizeShapeFeature(context);
    	List<GraphicsAlgorithm> ch = ga.getGraphicsAlgorithmChildren();
    	if ((ch != null) && (ch.size() > 0) && (ch.get(0) instanceof Text)) {
    		return new ResizeAttributeFeature(this);
    	}
		
		return super.getResizeShapeFeature(context);
    }
      
    
   
    
    @Override
    public ILayoutFeature getLayoutFeature(ILayoutContext context) {
        PictogramElement pictogramElement = context.getPictogramElement();
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof JavaPersistentType) {
            return new LayoutJPAEntityFeature(this);
        }
        return super.getLayoutFeature(context);
    }
    
    
    @Override
    public IRemoveFeature getRemoveFeature(IRemoveContext context) {
        PictogramElement pe = context.getPictogramElement();
        Object bo = getBusinessObjectForPictogramElement(pe);   
        if (bo == null)
        	super.getRemoveFeature(context);
    	if (bo instanceof JavaPersistentType) {
    		return new RemoveJPAEntityFeature(this);	
    	} else if (bo instanceof AbstractRelation) { 
    		return new RemoveRelationFeature(this);
    	}
    	GraphicsAlgorithm ga = pe.getGraphicsAlgorithm();
    	if (ga == null) 
    		return super.getRemoveFeature(context);
    	List<GraphicsAlgorithm> ch = ga.getGraphicsAlgorithmChildren();
    	if ((ch != null) && (ch.size() > 0) && (ch.get(0) instanceof Text)) {
    		return new RemoveAttributeFeature(this);
    	}
    	return super.getRemoveFeature(context);
    }
        
    @Override
    public ICreateConnectionFeature[] getCreateConnectionFeatures() {
        return new ICreateConnectionFeature[] {
        	new CreateOneToOneUniDirRelationFeature(this),
            new CreateOneToOneBiDirRelationFeature(this),           
        	new CreateOneToManyUniDirRelationFeature(this),                    
        	new CreateManyToOneUniDirRelationFeature(this),
            new CreateManyToOneBiDirRelationFeature(this),            
        	new CreateManyToManyUniDirRelationFeature(this),
            new CreateManyToManyBiDirRelationFeature(this)
        };
    }
    
    @Override
    public IReason canAdd(IAddContext context) {
    	return Reason.createTrueReason();
    }
    
    public ICustomFeature getAddAllEntitiesFeature() {
    	return new AddAllEntitiesFeature(this); 
    }
    
    @Override
    public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context) {
        PictogramElement pe = context.getPictogramElement();
        Object bo = getBusinessObjectForPictogramElement(pe);
        if (JavaPersistentType.class.isInstance(bo)) {
        	return new MoveEntityShapeFeature(this);
        }
        if(bo == null){
    		return new MoveAttributeFeature(this);
        } 
    	GraphicsAlgorithm ga = pe.getGraphicsAlgorithm();
    	if (ga == null) 
    		return super.getMoveShapeFeature(context);
    	List<GraphicsAlgorithm> ch = ga.getGraphicsAlgorithmChildren();
    	if ((ch != null) && (ch.size() > 0) && (ch.get(0) instanceof Text)) {
    		return new MoveAttributeFeature(this);
    	}
        return super.getMoveShapeFeature(context);
    }
    
    
    @Override
    public IDirectEditingFeature getDirectEditingFeature(IDirectEditingContext context) {
        PictogramElement pe = context.getPictogramElement();
        GraphicsAlgorithm ga = pe.getGraphicsAlgorithm();
        if (!(ga instanceof Rectangle)) 
        	return super.getDirectEditingFeature(context);
        List<GraphicsAlgorithm> ch = ga.getGraphicsAlgorithmChildren();
        if ((ch == null) || (ch.size() == 0)) 
        	return super.getDirectEditingFeature(context);
        ga = ch.get(0);
        if (!(ga instanceof Text)) 
        	return super.getDirectEditingFeature(context);
        if(((Text)ga).getFont().isItalic())
    		return super.getDirectEditingFeature(context);
        Object bo = getBusinessObjectForPictogramElement(pe);
        if (bo != null) {
        	if (bo instanceof JavaPersistentAttribute) {
        		return new DirectEditAttributeFeature(this);
        	}
        }
        if (!(pe instanceof Shape)) 
        	return super.getDirectEditingFeature(context);
        Shape sh = (Shape)pe;
        ContainerShape csh = sh.getContainer();
        if (csh == null) 
        	return super.getDirectEditingFeature(context); 
        bo = getBusinessObjectForPictogramElement(csh);
        if (bo instanceof JavaPersistentType){ 
            return new DirectEditJPAEntityFeature(this);
        }
        return super.getDirectEditingFeature(context);
    }
    
    public void addMissingRelations(JavaPersistentType newEntity, ContainerShape newShape) {
        Collection<PictogramElement> allContainedPictogramElements = Graphiti.getPeService()
        .getAllContainedPictogramElements(getDiagramTypeProvider().getDiagram());
        
        for (PictogramElement pe : allContainedPictogramElements) {
        	if ((pe != newShape) && (pe instanceof ContainerShape)) {
        		JavaPersistentType entity = (JavaPersistentType)getBusinessObjectForPictogramElement(pe);
        		AbstractRelation newRel = produceNecessaryRelation(newEntity, entity);
        		if (newRel != null) {
        			JavaPersistentType owner = newRel.getOwner();
        			JavaPersistentType inverse = newRel.getInverse();
        			ContainerShape ownerShape = (ContainerShape)getPictogramElementForBusinessObject(owner);
        			ContainerShape inverseShape = (ContainerShape)getPictogramElementForBusinessObject(inverse);
         			AddRelationFeature f = new AddRelationFeature(this);
        			AddConnectionContext ctx = 
        				new AddConnectionContext(JPAEditorUtil.getAnchor(ownerShape), JPAEditorUtil.getAnchor(inverseShape));
        			f.add(ctx);
        		}
        	}
        }
    }
    
	public void renewAttributeJoiningStrategyPropertyListener(JavaPersistentAttribute jpa) {
		((JPASolver)getIndependenceSolver()).renewAttributeJoiningStrategyPropertyListener(jpa);
	}
        
    private AbstractRelation produceNecessaryRelation(JavaPersistentType ent1, JavaPersistentType ent2) {
    	return null;
    }
    
	public String produceKeyForRel(JavaPersistentType jpt, String attributeName) {
		return ((JPASolver)getIndependenceSolver()).produceKeyForRel(jpt, attributeName);
	}

    
    public ClickAddAttributeButtonFeature getClickAddAttributeButtonFeature() {
    	if (clickAddAttBtnFeat == null) {
    		clickAddAttBtnFeat = new ClickAddAttributeButtonFeature(this);
    	}
    	return clickAddAttBtnFeat;
    }
    
    public ClickRemoveAttributeButtonFeature getClickRemoveAttributeButtonFeature() {
    	if (clickRemoveAttBtnFeat == null) {
    		clickRemoveAttBtnFeat = new ClickRemoveAttributeButtonFeature(this);
    	}
    	return clickRemoveAttBtnFeat;
    }    
    
    @Override
    public IMoveConnectionDecoratorFeature getMoveConnectionDecoratorFeature(
    		IMoveConnectionDecoratorContext context) {
      	 return new JPAMoveConnectionDecoratorFeature(this);
    }

    public Object getBusinessObjectForKey(String key) {
    	return getIndependenceSolver().getBusinessObjectForKey(key);
    }
    
    public String getKeyForBusinessObject(Object bo) {
    	return getIndependenceSolver().getKeyForBusinessObject(bo);
    }
        
    public void putKeyToBusinessObject(String key, Object bo) {
    	((JPASolver)getIndependenceSolver()).addKeyBusinessObject(key, bo);
    }  
    
    public Object remove(String key) {
    	return remove(key, false);
    }
    
    public Object remove(String key, boolean save) {
    	Object res = getBusinessObjectForKey(key);
    	if (res instanceof JavaPersistentType) {
    		final JavaPersistentType jpt = (JavaPersistentType)res;
    		if (save)
    			JpaArtifactFactory.instance().forceSaveEntityClass(jpt, this);
    		if(!(jpt.getMapping() instanceof JavaEntity)){
    			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
    				public void run() {
    			          removeFromDiagramIfNotEntity(jpt);
    				}});
    		}

    	}
    	return ((JPASolver)getIndependenceSolver()).remove(key);
    }    
    
    
    protected void removeAllConnections(Shape shape) {
		for (Iterator<Anchor> iter = shape.getAnchors().iterator(); iter.hasNext();) {
			Anchor anchor = iter.next();
			for (Iterator<Connection> iterator = Graphiti.getPeService().getAllConnections(anchor).iterator(); iterator.hasNext();) {
				Connection connection = iterator.next();
				if (GraphitiInternal.getEmfService().isObjectAlive(connection)) {
 					Object ob = getBusinessObjectForPictogramElement(connection);
						if(ob instanceof AbstractRelation){
							String key = getKeyForBusinessObject(ob);
							((JPASolver)getIndependenceSolver()).remove(key);
						}
					Graphiti.getPeService().deletePictogramElement(connection);
				}
			}
		}
	}

    
    private void removeFromDiagramIfNotEntity(final JavaPersistentType jpt) {
		final PictogramElement cs = this.getPictogramElementForBusinessObject(jpt);
		if (cs != null) {
			final Shape shape = (Shape) cs;
			TransactionalEditingDomain ted = ModelIntegrationUtil.getTransactionalEditingDomain(this
							                                           .getDiagramTypeProvider().getDiagram());
			ted.getCommandStack().execute(new RecordingCommand(ted) {
				@Override
				protected void doExecute() {
					removeAllConnections(shape);
					Graphiti.getPeService().deletePictogramElement(cs);
				}
			});
		}
	}


    
    public boolean hasObjectWithName(String name) {
    	return ((JPASolver)getIndependenceSolver()).containsKey(name);
    }
    
    public void replaceAttribute(final JavaPersistentAttribute oldAt, final JavaPersistentAttribute newAt) {
    	final PictogramElement pe = this.getPictogramElementForBusinessObject(oldAt);
    	if (pe == null) {
    		//System.err.println("PictogramElement is null\n");
    		throw new RuntimeException();    		
    		//return;
    	}
    	TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(pe);
    	ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
		    	JPASolver solver = (JPASolver)getIndependenceSolver(); 
		    	solver.remove(solver.getKeyForBusinessObject(oldAt));
		    	solver.addKeyBusinessObject(solver.getKeyForBusinessObject(newAt), newAt);
		    	if (newAt == null) {
					JPADiagramEditorPlugin.logError("New attribute is null\n", new Exception()); //$NON-NLS-1$
		    		return;
		    	}
		    	link(pe, newAt);
		    	GraphicsAlgorithm ga = pe.getGraphicsAlgorithm();
		    	if (ga == null)
		    		return;
		    	List<GraphicsAlgorithm> ch = ga.getGraphicsAlgorithmChildren();
		    	((Text)ch.get(0)).setValue(newAt.getName());
			}
    	});
    }
    
    public boolean doesRelationExist(JavaPersistentType owner, 
			JavaPersistentType inverse, 
			String ownerAttributeName,
			RelType relType, 
			RelDir relDir) {
    	
    	String id = AbstractRelation.generateId(owner, inverse, ownerAttributeName, relType, relDir);
    	return (getBusinessObjectForKey(id) != null);
    }   
    
	public boolean isRelationRelatedToAttribute(JavaPersistentAttribute jpa) {
		return ((JPASolver)getIndependenceSolver()).isRelationRelatedToAttribute(jpa);
	}
	
	public IRelation getRelationRelatedToAttribute(JavaPersistentAttribute jpa) {
		return ((JPASolver)getIndependenceSolver()).getRelationRelatedToAttribute(jpa);
	}    
		
	public ICompilationUnit getCompilationUnit(JavaPersistentType jpt) {
		return ((JPASolver)getIndependenceSolver()).getCompilationUnit(jpt);
	}

	public void restoreEntity(JavaPersistentType jpt) {
		((JPASolver)getIndependenceSolver()).restoreEntity(jpt);
	}
	
	public int getAttribsNum(Shape sh) {
		String s = Graphiti.getPeService().getPropertyValue(sh, JPAEditorConstants.PROP_ATTRIBS_NUM);
		int res = 0;
		if (s == null) {
			setAttribsNum(0, sh);
		} else {
			res = Integer.parseInt(s);
		}
		return res;
	}
	
	public int increaseAttribsNum(Shape sh) {
		int attribsNum = getAttribsNum(sh);
		attribsNum++;
		setAttribsNum(attribsNum, sh);
		return attribsNum;
	}
	
	public int decreaseAttribsNum(Shape sh) {
		int attribsNum = getAttribsNum(sh);
		attribsNum--;
		setAttribsNum(attribsNum, sh);
		return attribsNum;
	}	
	
	public void setAttribsNum(int newAttribsNum, Shape sh) {
		Graphiti.getPeService().setPropertyValue(sh, JPAEditorConstants.PROP_ATTRIBS_NUM, "" + newAttribsNum); //$NON-NLS-1$
	}
	
	/*
	public Collection<ResourceOperation> getAffectedPartitionsForModification() {
		return new HashSet<ResourceOperation>();
	}
	*/

	public void dispose() {
		stopThread();
		((JPASolver)getIndependenceSolver()).dispose();
		setIndependenceSolver(null);
	}


	public IPeServiceUtil getPeUtil() {
		return peUtil;
	}
	
	public IJPAEditorUtil getJPAEditorUtil() {
		return this.jpaEditorUtil;
	}
	
	public IModelIntegrationUtil getMoinIntegrationUtil() {
		return moinIntegrationUtil;
	}
	
	public IGraphicsUpdater getGraphicsUpdater() {
		return graphicsUpdater;
	}

	public TransactionalEditingDomain getTransactionalEditingDomain() {
		Diagram diagram = getDiagramTypeProvider().getDiagram();
		return TransactionUtil.getEditingDomain(diagram);
	}
	
	public PictogramElement getPictogramElementForBusinessObject(Object businessObject) {
		PictogramElement pe = super.getPictogramElementForBusinessObject(businessObject);
		/*
		int cnt = 0;
		while ((pe == null) && (cnt < 20)) {
			//System.out.println("loop");
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
			}
			System.out.println("loop; hc:" + businessObject.hashCode());
			pe = super.getPictogramElementForBusinessObject(businessObject);
			cnt++;
		}
		if (pe == null) {
			System.out.println("FP - PE is null; hc:" + businessObject.hashCode());
		}
		*/
		return pe;
	}
	
	public Properties loadProperties(IProject project) {
		return JPADiagramPropertyPage.loadProperties(project);
	}

}
