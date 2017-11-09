import React, { Component } from 'react';
import {Button, FlatList, ListItem, ListView, Text, TextInput, TouchableHighlight, View} from "react-native";
import Main from "./Main";

export default class Edit extends Component<{}> {
    constructor(props) {
        super(props);
        this.itemIndex=this.props.elem.split("-")[0]-1;
        this.list=this.props.list;
        this.state={description:this.list[this.itemIndex].description, name: this.list[this.itemIndex].name};
        this.mainComp = <Main change={this.props.change} update={this.props.update} add={this.props.add} list={this.props.list}/>;
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
                    onPress={ ()=>
                                {
                                    this.props
                                        .update(this.itemIndex,{name:this.state.name, description: this.state.description});
                                }
                    }
                    title="Update"
                    color="#841584"
                    accessibilityLabel="Update shopping list"
                />

                <Button
                    onPress={() => {this.props.change(this.mainComp);}}
                    title="Back"
                    color="#841584"
                    accessibilityLabel="Go back"
                />
            </View>

        );
    }
}