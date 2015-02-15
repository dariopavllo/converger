package org.converger.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum FrameworkOperation {
	
	SIMPLIFY {

		@Override
		public List<Field> requestFields(int index) {
			return Collections.emptyList();
		}
		
	}, 
	
	SUBSTITUTE {

		@Override
		public List<Field> requestFields(int index) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	
	EVALUATE {

		@Override
		public List<Field> requestFields(int index) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	
	SOLVE {

		@Override
		public List<Field> requestFields(int index) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	
	DIFFERENTIATE {
		@Override
		public List<Field> requestFields(int index) {
			return null;
		}
	},
	INTEGRATE {
		@Override
		public List<Field> requestFields(int index) {
			
			return null;
		}
	},
	
	TAYLOR {

		@Override
		public List<Field> requestFields(int index) {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	
	public abstract List<Field> requestFields(int index);
}
