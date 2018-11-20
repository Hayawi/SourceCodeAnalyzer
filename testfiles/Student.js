/*
* Copyright (c) 2017 Capital One Financial Corporation All Rights Reserved.
*
* This software contains valuable trade secrets and proprietary information of
* Capital One and is protected by law. It may not be copied or distributed in
* any form or medium, disclosed to third parties, reverse engineered or used in
* any manner without prior written authorization from Capital One.
*
*
*/

//Class-based object-oriented programming in Javascript
class Student {
	fullName: string; //Full Name of the student
	// Supports constructor function with a few public fields
	constructor(public firstName: string, public middleInitial: string, public lastName: string) {
		//TODO: Refactor this to use the ES6 template literal notation
		this.fullName = firstName + " " + middleInitial + " " + lastName;
	}
}

/*
* In TypeScript, two types are compatible if their internal structure is compatible.
* This allows us to implement an interface just by having the shape the interface requires, without an explicit implements clause.
*/
interface Person { /**/
	firstName: string;
	lastName: string;
}

/*
* Type annotations in TypeScript are lightweight ways to record the intended contract of the function or variable.* */
function greeter(person : Person) { //Add a : string type annotation to the ‘person’ function argument.
	return "Hello, " + person.firstName + " " + person.lastName;
}

let user = new Student("Jane", "M.", "User");

document.body.innerHTML = greeter(user);