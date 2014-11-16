//
//  FirstViewController.m
//  SFTHClient
//
//  Created by Tony Tam on 11/1/14.
//  Copyright (c) 2014 Eatbacon. All rights reserved.
//

#import "FirstViewController.h"

@interface FirstViewController ()

@end

@implementation FirstViewController
    
- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    // refresh state
    
    [self refreshState];
}

- (void)refreshState {
    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
    
    [api getSwitchStateWithCompletionBlock:@6 completionHandler:^(SWGDeviceState *output, NSError *error) {
        if(output) {
            if([[output state] isEqualToNumber:@0]) {
                [self.switch1 setOn:NO animated:YES];
            }
            else {
                [self.switch1 setOn:YES animated:YES];
            }
        }
    }];
    
    
    [api getSwitchStateWithCompletionBlock:@8 completionHandler:^(SWGDeviceState *output, NSError *error) {
        if(output) {
            if([[output state] isEqualToNumber:@0]) {
                [self.switch2 setOn:NO animated:YES];
            }
            else {
                [self.switch2 setOn:YES animated:YES];
            }
        }
    }];

    [api getSwitchStateWithCompletionBlock:@4 completionHandler:^(SWGDeviceState *output, NSError *error) {
        if(output) {
            if([[output state] isEqualToNumber:@0]) {
                [self.switch3 setOn:NO animated:YES];
            }
            else {
                [self.switch3 setOn:YES animated:YES];
            }
        }
    }];
    
    [api getSwitchStateWithCompletionBlock:@3 completionHandler:^(SWGDeviceState *output, NSError *error) {
        if(output) {
            if([[output state] isEqualToNumber:@0]) {
                [self.switch4 setOn:NO animated:YES];
            }
            else {
                [self.switch4 setOn:YES animated:YES];
            }
        }
    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction) switch1ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;

    int state = 0;
    if([l_switch isOn]) {
        state = 255;
    }
    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
    [api setSwitchValueWithCompletionBlock:@6 value:[[NSNumber alloc] initWithInt:state] completionHandler:^(NSError *error) {
        if(error)
            NSLog(@"%@", error);
        else
            NSLog(@"ok!");
    }];
}

-(IBAction) switch1Timer:(id)sender {
    [self.switch1 setOn:YES animated:YES];

    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
    [api setSwitchValueWithTimerWithCompletionBlock:@6 timer:@5 completionHandler:^(NSError *error) {
        if(error)
            NSLog(@"%@", error);
        else
            NSLog(@"ok!");
    }];
}

-(IBAction) switch2ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;
    
    int state = 0;
    if([l_switch isOn]) {
        state = 255;
    }
    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
    [api setDimmerValueWithCompletionBlock:@8 value:[[NSNumber alloc] initWithInt:state] completionHandler:^(NSError *error) {
        if(error)
            NSLog(@"%@", error);
        else
            NSLog(@"ok!");
    }];
}

-(IBAction) switch2Timer:(id)sender {
    [self.switch2 setOn:YES animated:YES];
    
    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];

    [api dimmerOnWithTimerWithCompletionBlock:@8 timer:@5 completionHandler:^(NSError *error) {
        if(error)
            NSLog(@"%@", error);
        else
            NSLog(@"ok!");
    }];
}


-(IBAction) switch3ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;
    
    int state = 0;
    if([l_switch isOn]) {
        state = 255;
    }
    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
    [api setDimmerValueWithCompletionBlock:@4 value:[[NSNumber alloc] initWithInt:state] completionHandler:^(NSError *error) {
        if(error)
            NSLog(@"%@", error);
        else
            NSLog(@"ok!");
    }];
}

-(IBAction) switch3Timer:(id)sender {
    [self.switch2 setOn:YES animated:YES];
    
    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
    
    [api dimmerOnWithTimerWithCompletionBlock:@4 timer:@5 completionHandler:^(NSError *error) {
        if(error)
            NSLog(@"%@", error);
        else
            NSLog(@"ok!");
    }];
}


-(IBAction) switch4ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;
    
    int state = 0;
    if([l_switch isOn]) {
        state = 255;
    }
    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
    [api setDimmerValueWithCompletionBlock:@3 value:[[NSNumber alloc] initWithInt:state] completionHandler:^(NSError *error) {
        if(error)
            NSLog(@"%@", error);
        else
            NSLog(@"ok!");
    }];
}

-(IBAction) switch4Timer:(id)sender {
    [self.switch4 setOn:YES animated:YES];
    
    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
    
    [api dimmerOnWithTimerWithCompletionBlock:@3 timer:@5 completionHandler:^(NSError *error) {
        if(error)
            NSLog(@"%@", error);
        else
            NSLog(@"ok!");
    }];
}

@end
