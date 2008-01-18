/*******************************************************************************
 *  Copyright (c) 2008 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.tests.internal.jface;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingLabelProvider;
import org.eclipse.jpt.ui.internal.jface.IItemLabelProvider;
import org.eclipse.jpt.ui.internal.jface.IItemLabelProviderFactory;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DelegatingLabelProviderUiTest extends ApplicationWindow
{
	private TreeViewer tree;
	
	private WritablePropertyValueModel<Vehicle> selectedVehicle;
	
	
	public static void main(String[] args) {
		Window window = new DelegatingLabelProviderUiTest(args);
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
		System.exit(0);
	}
	
	
	private DelegatingLabelProviderUiTest(String[] args) {
		super(null);
		this.selectedVehicle = new SimplePropertyValueModel<Vehicle>();
	}
	
	
	@Override
	protected Control createContents(Composite parent) {
		((Shell) parent).setText(ClassTools.shortClassNameForObject(this));
		parent.setSize(400, 400);
		parent.setLayout(new GridLayout());
		Composite mainPanel = new Composite(parent, SWT.NONE);
		mainPanel.setLayoutData(new GridData(GridData.FILL_BOTH));
		mainPanel.setLayout(new GridLayout());
		buildTreePanel(mainPanel);
		buildControlPanel(mainPanel);
		return mainPanel;
	}
	
	private void buildTreePanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setLayout(new GridLayout());
		
		Label label = new Label(panel, SWT.NONE);
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
		label.setText("My Vehicles");
		
		tree = new TreeViewer(panel, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.getTree().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		tree.setContentProvider(new VehicleTreeContentProvider());
		tree.setLabelProvider(new DelegatingLabelProvider(new VehicleLabelProviderFactory()));
		tree.setInput(new Root());
		tree.addSelectionChangedListener(buildTreeSelectionChangedListener());
	}
	
	private ISelectionChangedListener buildTreeSelectionChangedListener() {
		return new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				selectedVehicle.setValue((Vehicle) ((IStructuredSelection) event.getSelection()).getFirstElement());
			}
		};
	}
	
	private void buildControlPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		panel.setLayout(new GridLayout());
		buildUpperControlPanel(panel);
		buildLowerControlPanel(panel);
	}
	
	private void buildUpperControlPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		panel.setLayout(new GridLayout(2, true));
		buildVehicleCombo(panel);
		buildColorCombo(panel);
	}
	
	private void buildVehicleCombo(Composite parent) {
		final ComboViewer combo = new ComboViewer(parent, SWT.READ_ONLY);
		combo.getCombo().setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		combo.setContentProvider(new ArrayContentProvider());
		combo.setLabelProvider(new VehicleTypeLabelProvider());
		combo.setInput(
			new VehicleType[] {
				VehicleType.BICYCLE, VehicleType.CAR, 
				VehicleType.TRUCK, VehicleType.BOAT
			});
		combo.getCombo().setEnabled(false);
		combo.addSelectionChangedListener(
			new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					selectedVehicle().setVehicleType((VehicleType) ((StructuredSelection) event.getSelection()).getFirstElement()); 
				}
			});
		selectedVehicle.addPropertyChangeListener(
			PropertyValueModel.VALUE, 
			new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					Vehicle vehicle = selectedVehicle();
					combo.getCombo().setEnabled(vehicle != null);
					combo.setSelection(new StructuredSelection((vehicle == null) ? null : vehicle.vehicleType()));
				}
			});
	}
	
	private void buildColorCombo(Composite parent) {
		final ComboViewer combo = new ComboViewer(parent, SWT.READ_ONLY);
		combo.getCombo().setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		combo.setContentProvider(new ArrayContentProvider());
		combo.setLabelProvider(new ColorLabelProvider());
		combo.setInput(new Color[] {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN});
		combo.addSelectionChangedListener(
			new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					selectedVehicle().setColor((Color) ((StructuredSelection) event.getSelection()).getFirstElement()); 
				}
			});
		selectedVehicle.addPropertyChangeListener(
			PropertyValueModel.VALUE, 
			new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					Vehicle vehicle = selectedVehicle();
					combo.getCombo().setEnabled(vehicle != null);
					combo.setSelection(new StructuredSelection((vehicle == null) ? null : vehicle.color()));
				}
			});
	}
	
	private void buildLowerControlPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		panel.setLayout(new GridLayout(3, false));
		buildEffectsLabel(panel);
		buildGreyedCheckBox(panel);
		buildTranslucentCheckBox(panel);
		buildActionPanel(panel);
	}
	
	private void buildEffectsLabel(Composite parent) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText("Color effects: ");
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 3, 1));
	}
	
	private void buildGreyedCheckBox(Composite parent) {
		final Button button = new Button(parent, SWT.CHECK);
		button.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
		button.setText("greyed");
		button.setEnabled(false);
		button.addSelectionListener(
			new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					selectedVehicle().setGreyed(button.getSelection());
				}
			});
		selectedVehicle.addPropertyChangeListener(
			PropertyValueModel.VALUE, 
			new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					Vehicle vehicle = selectedVehicle();
					button.setEnabled(vehicle != null);
					button.setSelection(vehicle != null && vehicle.isGreyed());
				}
			});
	}
	
	private void buildTranslucentCheckBox(Composite parent) {
		final Button button = new Button(parent, SWT.CHECK);
		button.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		button.setText("translucent");
		button.setEnabled(false);
		button.addSelectionListener(
			new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					selectedVehicle().setTranslucent(button.getSelection());
				}
			});
		selectedVehicle.addPropertyChangeListener(
			PropertyValueModel.VALUE, 
			new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					Vehicle vehicle = selectedVehicle();
					button.setEnabled(vehicle != null);
					button.setSelection(vehicle != null && vehicle.isTranslucent());
				}
			});
	}
	
	private void buildActionPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.END, GridData.FILL, false, false));
		panel.setLayout(new GridLayout());
		buildRefreshTreeACI().fill(panel);
	}
	
	private ActionContributionItem buildRefreshTreeACI() {
		Action action = new Action("Refresh tree", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				refreshTree();
			}
		};
		action.setToolTipText("Refresh the tree's labels");
		return new ActionContributionItem(action);
	}
	
	void refreshTree() {
		tree.refresh();
	}
	
	private Vehicle selectedVehicle() {
		return selectedVehicle.value();
	}
	
	
	private static class VehicleTypeLabelProvider extends BaseLabelProvider
		implements ILabelProvider
	{
		public Image getImage(Object element) {
			return null;
		}
		
		public String getText(Object element) {
			return ((VehicleType) element).description();
		}
	}
	
	
	private static class ColorLabelProvider extends BaseLabelProvider
		implements ILabelProvider
	{
		public Image getImage(Object element) {
			return null;
		}
		
		public String getText(Object element) {
			return ((Color) element).description();
		}
	}
	
	
	private static class VehicleTreeContentProvider
		implements ITreeContentProvider
	{
		public VehicleTreeContentProvider() {
			super();
		}
		
		public Object getParent(Object element) {
			return ((TreeNode) element).parent();
		}
		
		public boolean hasChildren(Object element) {
			return element instanceof Root;
		}
		
		public Object[] getChildren(Object parentElement) {
			return (parentElement instanceof Root) ?
				((Root) parentElement).vehicles() :
				new Object[0];
		}
		
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}
		
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
		
		public void dispose() {}
	}
	
	
	private static class VehicleLabelProviderFactory
		implements IItemLabelProviderFactory
	{
		public IItemLabelProvider buildItemLabelProvider(Object element, DelegatingLabelProvider labelProvider) {
			return new VehicleLabelProvider((Vehicle) element, labelProvider);
		}
	}
	
	
	private static class VehicleLabelProvider extends AbstractItemLabelProvider
	{
		public VehicleLabelProvider(Vehicle vehicle, DelegatingLabelProvider labelProvider) {
			super(vehicle, labelProvider);
		}
		
		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new PropertyAspectAdapter<Vehicle, String>(
					new StaticPropertyValueModel<Vehicle>((Vehicle) model()), 
					Vehicle.VEHICLE_TYPE_PROPERTY, Vehicle.COLOR_PROPERTY) {
				@Override
				protected String buildValue_() {
					return subject.color().description() + ' ' + subject.vehicleType().description();
				}
			};
		}
		
		@Override
		protected PropertyValueModel<Image> buildImageModel() {
			return new PropertyAspectAdapter<Vehicle, Image>(
					new StaticPropertyValueModel<Vehicle>((Vehicle) model()), 
					Vehicle.COLOR_PROPERTY, Vehicle.GREYED_PROPERTY, Vehicle.TRANSLUCENT_PROPERTY) {
				@Override
				protected Image buildValue_() {
					return subject.image();
				}
			};
		}
	}
	
	
	private static abstract class TreeNode extends AbstractModel
	{
		private TreeNode parent;
		
		
		public TreeNode(TreeNode parent) {
			this.parent = parent;
		}
		
		
		public TreeNode parent() {
			return parent;
		}
	}
	
	
	private static class Root extends TreeNode
	{
		protected final Vehicle[] vehicles;
		
		
		public Root() {
			super(null);
			vehicles = new Vehicle[] {
				new Vehicle(this, VehicleType.BICYCLE, Color.BLUE),
				new Vehicle(this, VehicleType.CAR, Color.YELLOW),
				new Vehicle(this, VehicleType.TRUCK, Color.RED),
				new Vehicle(this, VehicleType.BOAT, Color.GREEN)};
		}
		
		public Vehicle[] vehicles() {
			return vehicles;
		}
	}
	
	
	private static class Vehicle extends TreeNode
	{
		private VehicleType vehicleType;
		public final static String VEHICLE_TYPE_PROPERTY = "vehicleType";
		
		private Color color;
		public final static String COLOR_PROPERTY = "color";
		
		private boolean greyed = false;
		public final static String GREYED_PROPERTY = "greyed";
		
		private boolean translucent = false;
		public final static String TRANSLUCENT_PROPERTY = "translucent";
		
		private Image image;
		
			
		public Vehicle(TreeNode parent, VehicleType vehicleType, Color color) {
			super(parent);
			this.vehicleType = vehicleType;
			this.color = color;
		}
		
		public VehicleType vehicleType() {
			return vehicleType;
		}
		
		public void setVehicleType(VehicleType newVehicleType) {
			VehicleType oldVehicleType = vehicleType;
			vehicleType = newVehicleType;
			firePropertyChanged(VEHICLE_TYPE_PROPERTY, oldVehicleType, newVehicleType);
		}
		
		public Color color() {
			return color;
		}
		
		public void setColor(Color newColor) {
			Color oldColor = color;
			color = newColor;
			firePropertyChanged(COLOR_PROPERTY, oldColor, newColor);
		}
		
		public boolean isGreyed() {
			return greyed;
		}
		
		public void setGreyed(boolean newGreyed) {
			boolean oldGreyed = greyed;
			greyed = newGreyed;
			firePropertyChanged(GREYED_PROPERTY, oldGreyed, newGreyed);
		}
		
		public boolean isTranslucent() {
			return translucent;
		}
		
		public void setTranslucent(boolean newTranslucent) {
			boolean oldTranslucent = translucent;
			translucent = newTranslucent;
			firePropertyChanged(TRANSLUCENT_PROPERTY, oldTranslucent, newTranslucent);
		}
		
		public Image image() {
			if (image != null) {
				image.dispose();
			}
			
			return ImageFactory.image(color(), greyed, translucent);
		}
	}
	
	
	private static enum VehicleType
	{
		BICYCLE("bicycle"),
		CAR("car"),
		TRUCK("truck"),
		BOAT("boat");
		
		private final String description;
		
		private VehicleType(String description) {
			this.description = description;
		}
		
		public String description() {
			return description;
		}
		
		@Override
		public String toString() {
			return description();
		}
	}
	
	
	private static enum Color
	{
		RED("red", new RGB(255, 0, 0)),
		BLUE("blue", new RGB(0, 0, 255)),
		YELLOW("yellow", new RGB(255, 255, 0)),
		GREEN("green", new RGB(0, 255, 0));
		
		private final String description;
		
		private final RGB rgb;
		
		private Color(String description, RGB rgb) {
			this.description = description;
			this.rgb = rgb;
		}
		
		public String description() {
			return description;
		}
		
		public RGB rgb() {
			return rgb;
		}
		
		@Override
		public String toString() {
			return description();
		}
	}
	
	
	private static class ImageFactory
	{
		private static RGB rgb(Color color, boolean greyed, boolean translucent) {
			RGB rgb = (greyed) ? new RGB(127, 127, 127) : color.rgb();
			if (translucent) {
				rgb = new RGB(translucify(rgb.red), translucify(rgb.green), translucify(rgb.blue));
			}
			return rgb;
		}
		
		private static int translucify(int color) {
			return 255 - (int) ((255 - color) * 0.3);
		}
		
		public static Image image(Color color, boolean greyed, boolean translucent) {
			PaletteData pd = new PaletteData(new RGB[] {rgb(color, greyed, translucent)});
			ImageData id = new ImageData(20, 20, 1, pd);
			for (int x = 0; x < 20; x ++) {
				for (int y = 0; y < 20; y ++) {
					id.setPixel(x, y, 0);
				}
			}
			return new Image(Display.getCurrent(), id);
		}
	}
}
