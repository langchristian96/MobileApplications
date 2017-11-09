import React, { Component } from 'react';
import {Button, Text, TextInput, View} from "react-native";
import SeeAll from "./SeeAll";
import {Linking} from 'react-native';

export default class Main extends Component<{}> {

    constructor(props) {
        super(props);
        this.state = { name: 'Shopping List name', description: 'Shopping List Description' };
        this.comp = <SeeAll change={this.props.change} update={this.props.update} add={this.props.add} list={this.props.list}/>
    }

    addShoppingList() {
        let shoppingList = {name:this.state.name, description:this.state.description};
        this.props.add(shoppingList);
        this.sendMail();

    }

    sendMail() {

        let url = "mailto:langchristian96@gmail.com";
        url += "?subject=ShoppingListAdded";
        url += "&body=";
        url += this.state.name+" - "+this.state.description;
        console.log("State: ", this.state);
        console.log("URL: ", url);
        Linking.openURL(url);
    }
    render() {
        return (
            <View>
                <TextInput
                style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                onChangeText={(text) => this.setState({name: text})}
                value={this.state.name}
                />
                <TextInput
                style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                onChangeText={(text) => this.setState({description: text})}
                value={this.state.description}
                />
                <Button
                    onPress={this.addShoppingList.bind(this)}
                    title="Add"
                    color="#841584"
                    accessibilityLabel="Add shopping list"
                />
                <Button
                    onPress={() => {this.props.change(this.comp);}}
                    title="See all"
                    color="#841584"
                    accessibilityLabel="Add shopping list"
                />
            </View>

        );
    }
}