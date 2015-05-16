/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.SparseArray;

import com.callil.rotatingsentries.entityComponentSystem.components.Component;

/**
 * @author Callil
 *
 */
public class EntityManager {
	
	/**
	 * The list of entity ids currently in the game.
	 */
	private List<Integer> entities;
	
	/**
	 * Map that gives, for a given component class name the map registering
	 * for each entity id the component of this class it holds.
	 */
	private Map<Class<? extends Component>, SparseArray<List<Component>>> componentsByClass;

	/**
	 * The lowest eid that is yet to be assigned.
	 */
	private int lowestUnassignedEid;
	
	/**
	 * Constructor.
	 */
	public EntityManager() {
		this.entities = new ArrayList<Integer>();
		this.componentsByClass = new HashMap<Class<? extends Component>, SparseArray<List<Component>>>();
		this.lowestUnassignedEid = 0;
	}
	
	
	//Methods
	
	/**
	 * Generate a new unique eid.
	 * @return the eid generated.
	 */
	public int generateNewEid() {
		return lowestUnassignedEid++;
	}

	/**
	 * Create a new entity and add it to the currently active entities list.
	 * @return the entity created.
	 */
	public Entity createEntity() {
		int eid = generateNewEid();
		entities.add(eid);
		return new Entity(eid);
	}
	
	/**
	 * Add a component to an entity.
	 * @param component the component to add.
	 * @param entity the entity to which the component will be added.
	 */
	public void addComponentToEntity(Component component, Entity entity) {
		addComponentToEntity(component, component.getClass(), entity);
	}
	
	/**
	 * Add a component to an entity.
	 * @param component the component to add.
	 * @param componentClass the class of the component to add.
	 * @param entity the entity to which the component will be added.
	 */
	@SuppressWarnings("unchecked")
	private void addComponentToEntity(Component component, Class<? extends Component> componentClass, Entity entity) {
		SparseArray<List<Component>> components = this.getComponentsByClass().get(componentClass);
		if (components == null) {
			components = new SparseArray<List<Component>>();
			this.getComponentsByClass().put(componentClass, components);
		}
		if (components.get(entity.getEid()) == null) {
			List<Component> list = new ArrayList<Component>();
			components.put(entity.getEid(), list);
		}
		components.get(entity.getEid()).add(component);
		
		if (componentClass.getSuperclass() != Component.class) {
			addComponentToEntity(component, (Class<? extends Component>) componentClass.getSuperclass(), entity);
		}
	}
	
	
	/**
	 * Replace a component for the given entity (delete the components of the same).
	 * @param component the component to add.
	 * @param entity the entity to which the component will be added.
	 */
	@SuppressWarnings("unchecked")
	public void replaceComponentForEntity(Component component, Entity entity) {
		replaceComponentForEntity(component,  component.getClass(), entity);
	}
	
	/**
	 * Add a component to an entity.
	 * @param component the component to add.
	 * @param componentClass the class of the component to add.
	 * @param entity the entity to which the component will be added.
	 */
	@SuppressWarnings("unchecked")
	private void replaceComponentForEntity(Component component, Class<? extends Component> componentClass, Entity entity) {
		SparseArray<List<Component>> components = this.getComponentsByClass().get(componentClass);
		if (components == null) {
			components = new SparseArray<List<Component>>();
			this.getComponentsByClass().put(componentClass, components);
		}
		
		List<Component> compoList = components.get(entity.getEid());
		if (compoList == null) {
			List<Component> list = new ArrayList<Component>();
			components.put(entity.getEid(), list);
		} else {
			//If contains compo, remove them before adding the new one
			for (Component oldCompo : compoList) {
				this.removeComponentFromEntity(oldCompo, entity);
			}
			components.put(entity.getEid(), compoList);
		}
		components.get(entity.getEid()).add(component);
		
		if (componentClass.getSuperclass() != Component.class) {
			addComponentToEntity(component, (Class<? extends Component>) componentClass.getSuperclass(), entity);
		}
	}
	
	
	
	/**
	 * Remove a component from an entity.
	 * @param componentClass the class of the component.
	 * @param entity the entity
	 */
	public void removeComponentFromEntity(Component component, Entity entity) {
		Class<?> componentClass = component.getClass();
		while(componentClass != Component.class) {
			SparseArray<List<Component>> components = this.getComponentsByClass().get(componentClass);
			if (components != null) {
				List<Component> list = components.get(entity.getEid());
				list.remove(component);
				if (list.isEmpty()) {
					components.remove(entity.getEid());
				}
			}
			componentClass = componentClass.getSuperclass();
		}
	}
	
	/**
	 * For a given entity, retrieve the FIRST added component of the given class.
	 * @param <T> The component class
	 * @param componentClass the class of the component.
	 * @param entity the entity we want to retrieve the component from.
	 * @return The first component to have been linked to this entity, null if there isn't any.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> T getComponent(Class<T> componentClass, Entity entity) {
		SparseArray<List<Component>> map = this.getComponentsByClass().get(componentClass);
		if (map != null) {
			if (map.get(entity.getEid()) != null) {
				return (T) map.get(entity.getEid()).get(0);
			} else {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * For a given entity, retrieve the LIST of components of the given class.
	 * @param <T>
	 * @param <T> The component class
	 * @param componentClass the class of the component.
	 * @param entity the entity we want to retrieve the component from.
	 * @return The components linked to this entity, null if there isn't any.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getComponents(Class<T> componentClass, Entity entity) {
		SparseArray<List<Component>> map = this.getComponentsByClass().get(componentClass);
		if (map != null) {
			if (map.get(entity.getEid()) != null) {
				return (List<T>) map.get(entity.getEid());
			} else {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Remove an entity.
	 * @param entity the entity to remove
	 */
	public void removeEntity(Entity entity) {
		Collection<SparseArray<List<Component>>> listeMapEntity = this.getComponentsByClass().values();
		for (SparseArray<List<Component>> mapEntity : listeMapEntity) {
			//For each component class, remove the entity's id from the map if it exists
			List<Component> componentsDelete = mapEntity.get(entity.getEid());
			if (componentsDelete != null) {
				for (Component c : componentsDelete) {
					c.destroy();
				}
				componentsDelete.clear();
				mapEntity.remove(entity.getEid());
			}
		}
		entities.remove(Integer.valueOf(entity.getEid()));
		System.gc();
	}
	
	/**
	 * For a given component class name, retrieve all entities that have a component of this class.
	 * @param componentClass the class of the component
	 * @return A list of entities that have a component of this class. An empty list if no entity has this type
	 * of component.
	 */
	public List<Entity> getAllEntitiesPosessingComponentOfClass(Class<? extends Component> componentClass) {
		List<Entity> results = new ArrayList<Entity>();
		SparseArray<List<Component>> components = this.getComponentsByClass().get(componentClass);
		if (components != null) {
			for(int i = 0; i < components.size(); i++) {
				for (Component component : components.valueAt(i)) {
					if (component.isActive()) {
				    	results.add(new Entity(components.keyAt(i)));
				    	break;
				    }
				}
			}
		}
		return results;
	}

	
	
	//Getters & Setters
	
	/**
	 * @return the entities
	 */
	public List<Integer> getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(List<Integer> entities) {
		this.entities = entities;
	}

	/**
	 * @return the componentsByClass
	 */
	public Map<Class<? extends Component>, SparseArray<List<Component>>> getComponentsByClass() {
		return componentsByClass;
	}

	/**
	 * @param componentsByClass the componentsByClass to set
	 */
	public void setComponentsByClass(Map<Class<? extends Component>, SparseArray<List<Component>>> componentsByClass) {
		this.componentsByClass = componentsByClass;
	}

	/**
	 * @return the lowestUnassignedEid
	 */
	public int getLowestUnassignedEid() {
		return lowestUnassignedEid;
	}

	/**
	 * @param lowestUnassignedEid the lowestUnassignedEid to set
	 */
	public void setLowestUnassignedEid(int lowestUnassignedEid) {
		this.lowestUnassignedEid = lowestUnassignedEid;
	}

}
