import React, {Component} from 'react';
import {Button, Text, TextInput, View} from "react-native";
import SeeAll from "./SeeAll";
import {Linking} from 'react-native';
import DatePicker from 'react-native-datepicker'
import {Bar} from 'react-native-pathjs-charts';

export default class Main extends Component<{}> {

    constructor(props) {
        super(props);
        console.log(this.props.list);
        this.loadChartOptions();
        const chartData = this.getNameMap();//this.testChartData();//this.computeChartInput(this.props.list());
        this.state = {name: 'Shopping List name', description: 'Shopping List Description', time: '',
            chartData: chartData};
        this.comp =
            <SeeAll change={this.props.change} update={this.props.update} add={this.props.add} list={this.props.list} delete={this.props.delete}/>
    }


    getNameMap() {
        chartData = [];
        nameMap={};
        lst = this.props.list;
        if(lst.length == 0) {
            return [
                [{
                    "v": 10,
                    "name": "No entities"
                }]];
        }
        for(i=0;i<lst.length;i++) {
            if(nameMap[lst[i].name] != undefined) {
                nameMap[lst[i].name]++;
            }
            else {
                nameMap[lst[i].name]=1;
            }
        }
        for(var key in nameMap) {
            var val = nameMap[key];

            chartData.push([{"name": key, "v":val}]);
        }
        console.log(chartData);
        return chartData;
    }

    loadChartOptions(){
        this.options = {
            width: 300,
            height: 200,
            margin: {
                top: 20,
                left: 25,
                bottom: 50,
                right: 20
            },
            color: '#2980B9',
            gutter: 20,
            animate: {
                type: 'oneByOne',
                duration: 200,
                fillTransition: 3
            },
            axisX: {
                showAxis: true,
                showLines: true,
                showLabels: true,
                showTicks: true,
                zeroAxis: false,
                orient: 'bottom',
                label: {
                    fontFamily: 'Arial',
                    fontSize: 8,
                    fontWeight: true,
                    fill: '#34495E'
                }
            },
            axisY: {
                showAxis: true,
                showLines: true,
                showLabels: true,
                showTicks: true,
                zeroAxis: false,
                orient: 'left',
                label: {
                    fontFamily: 'Arial',
                    fontSize: 8,
                    fontWeight: true,
                    fill: '#34495E'
                }
            }
        }
    }

    testChartData(){
        let data = [
            [{
                "v": 49,
                "name": "apple"
            }],
            [{
                "v": 69,
                "name": "banana"
            }],
            [{
                "v": 29,
                "name": "grape"
            }]
        ];

        return data;
    }


    addShoppingList() {
        let shoppingList = {name: this.state.name, description: this.state.description, time:this.state.time};
        this.props.add(shoppingList);
        this.sendMail();

    }

    sendMail() {

        let url = "mailto:langchristian96@gmail.com";
        url += "?subject=ShoppingListAdded";
        url += "&body=";
        url += this.state.name + " - " + this.state.description;
        console.log("State: ", this.state);
        console.log("URL: ", url);
        Linking.openURL(url);
    }

    render() {
        mock_obj = this.testChartData();
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
                <View>
                    <Text>Time: </Text>
                    <DatePicker
                        style={{width: 300}}
                        time={this.state.time}
                        mode="time"
                        placeholder="select time"
                        format="hh:mm"
                        confirmBtnText="Confirm"
                        cancelBtnText="Cancel"
                        customStyles={{
                            timeIcon: {
                                position: 'absolute',
                                left: 0,
                                top: 4,
                                marginLeft: 0
                            },
                            timeInput: {
                                marginLeft: 36
                            }
                        }}
                        onDateChange={(time) => {
                            this.setState({time: time})
                            console.log(time);
                        }}
                    />
                </View>
                <Button
                    onPress={this.addShoppingList.bind(this)}
                    title="Add"
                    color="#841584"
                    accessibilityLabel="Add shopping list"
                />
                <Button
                    onPress={() => {
                        this.props.change(this.comp);
                    }}
                    title="See all"
                    color="#841584"
                    accessibilityLabel="Add shopping list"
                />
                <View>
                    <Bar data={this.state.chartData} options={this.options} accessorKey='v'/>
                </View>
            </View>

        );
    }
}