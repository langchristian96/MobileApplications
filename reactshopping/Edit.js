import React, {Component} from 'react';
import {Button, FlatList, ListItem, ListView, Text, TextInput, TouchableHighlight, View, Alert} from "react-native";
import Main from "./Main";
import {Bar} from 'react-native-pathjs-charts';
import firebase from 'react-native-firebase';

export default class Edit extends Component<{}> {
    constructor(props) {
        super(props);
        this.itemIndex = this.props.elem.split("-")[0] - 1;
        this.initialList = this.props.list;
        this.state = {description: this.initialList[this.itemIndex].description, name: this.initialList[this.itemIndex].name, time: this.initialList[this.itemIndex].time, product: "", productList: "", list: this.props.list};

        if(this.state.list[this.itemIndex].products != undefined) {
            this.state.productList += "Product List:";
            let products = this.state.list[this.itemIndex].products;
            for(j=0;j<products.length;j++) {
                this.state.productList += products[j];
                this.state.productList += " ";
            }
        }


        firebase.database().ref('shoppingLists').on('value', (dataSnapshot)=>{
            console.log("NEW UPDATE SL");
            let xx = this;
            let newPL = "";
            xx.setState({list: dataSnapshot.val()});

            if(this.state.list[this.itemIndex].products != undefined) {
                this.state.productList += "Product List:";
                let products = this.state.list[this.itemIndex].products;
                for(j=0;j<products.length;j++) {
                    newPL += products[j];
                    newPL += " ";
                }
                this.setState({productList: newPL});
            }
        });
        this.mainComp =
            <Main change={this.props.change} update={this.props.update} add={this.props.add} list={this.props.list} delete={this.props.delete} isAdmin={this.props.isAdmin} login={this.props.login}  addProduct={this.props.addProduct}/>;
    }

    render() {
        isAdmin = this.props.isAdmin();
        if(isAdmin) {
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

                    <Text>{this.state.time}</Text>
                    <Button
                        onPress={() => {
                            this.props
                                .update(this.itemIndex, {name: this.state.name, description: this.state.description});
                        }
                        }
                        title="Update"
                        color="#841584"
                        accessibilityLabel="Update shopping list"
                    />

                    <Button
                        onPress={() => {
                            let delfunction = this.props.delete;
                            let changefunction = this.props.change;
                            Alert.alert(
                                'Are you sure?',
                                'Are you sure you want to delete this?',
                                [
                                    {
                                        text: 'Yes', onPress: () => {
                                        // this.props
                                        delfunction(this.itemIndex);
                                        changefunction(this.mainComp);
                                    }
                                    },
                                    {text: 'Cancel', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
                                ],
                                {cancelable: false}
                            );
                        }
                        }
                        title="Delete"
                        color="#841584"
                        accessibilityLabel="Delete shopping list"
                    />
                    <Text>{this.state.productList}</Text>
                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                        onChangeText={(text) => this.setState({product: text})}
                        value={this.state.product}
                    />
                    <Button
                        onPress={() => {
                            this.props.addProduct(this.itemIndex, this.state.product);
                            let newPL = this.state.productList;
                            newPL+=this.state.product;
                            newPL+=" ";
                            this.setState({productList: newPL});
                        }}
                        title="Add Product"
                        color="#841584"
                        accessibilityLabel="Add Product"
                    />

                    <Button
                        onPress={() => {
                            this.props.change(this.mainComp);
                        }}
                        title="Back"
                        color="#841584"
                        accessibilityLabel="Go back"
                    />
                </View>

            );
        }
        else {
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

                    <Text>{this.state.time}</Text>
                    <Button
                        onPress={() => {
                            this.props
                                .update(this.itemIndex, {name: this.state.name, description: this.state.description});
                        }
                        }
                        title="Update"
                        color="#841584"
                        accessibilityLabel="Update shopping list"
                    />

                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                        onChangeText={(text) => this.setState({product: text})}
                        value={this.state.product}
                    />
                    <Button
                        onPress={() => {
                            this.props.addProduct(this.itemIndex, this.state.product);
                        }}
                        title="Add Product"
                        color="#841584"
                        accessibilityLabel="Add Product"
                    />

                    <Button
                        onPress={() => {
                            this.props.change(this.mainComp);
                        }}
                        title="Back"
                        color="#841584"
                        accessibilityLabel="Go back"
                    />
                </View>

            );

        }
    }
}