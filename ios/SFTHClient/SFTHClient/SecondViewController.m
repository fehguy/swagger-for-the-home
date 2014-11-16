//
//  SecondViewController.m
//  SFTHClient
//
//  Created by Tony Tam on 11/1/14.
//  Copyright (c) 2014 Eatbacon. All rights reserved.
//

#import "SecondViewController.h"

@interface SecondViewController ()

@end

@implementation SecondViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self refreshState];
    // Do any additional setup after loading the view, typically from a nib.
}

- (void)refreshState {
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];

    [api getRelayOutputWithCompletionBlock:@4 completionHandler:^(SWGDigitalIO *output, NSError *error) {
        if(output) {
            if([[output value] isEqualToNumber:[[NSNumber alloc] initWithBool:TRUE]])
                [self.switch1 setOn:YES animated:YES];
            else
                [self.switch1 setOn:NO animated:NO];
        }
    }];
    
    [api getRelayOutputWithCompletionBlock:@6 completionHandler:^(SWGDigitalIO *output, NSError *error) {
        if(output) {
            if([[output value] isEqualToNumber:[[NSNumber alloc] initWithBool:TRUE]])
                [self.switch2 setOn:YES animated:YES];
            else
                [self.switch2 setOn:NO animated:NO];
        }
    }];
    
    [api getRelayOutputWithCompletionBlock:@7 completionHandler:^(SWGDigitalIO *output, NSError *error) {
        if(output) {
            if([[output value] isEqualToNumber:[[NSNumber alloc] initWithBool:TRUE]])
                [self.switch3 setOn:YES animated:YES];
            else
                [self.switch3 setOn:NO animated:NO];
        }
    }];
    
    [api getRelayOutputWithCompletionBlock:@9 completionHandler:^(SWGDigitalIO *output, NSError *error) {
        if(output) {
            if([[output value] isEqualToNumber:[[NSNumber alloc] initWithBool:TRUE]])
                [self.switch4 setOn:YES animated:YES];
            else
                [self.switch4 setOn:NO animated:NO];
        }
    }];
    
    [api getRelayOutputWithCompletionBlock:@12 completionHandler:^(SWGDigitalIO *output, NSError *error) {
        if(output) {
            if([[output value] isEqualToNumber:[[NSNumber alloc] initWithBool:TRUE]])
                [self.switch5 setOn:YES animated:YES];
            else
                [self.switch5 setOn:NO animated:NO];
        }
    }];
    
    [api getRelayOutputWithCompletionBlock:@10 completionHandler:^(SWGDigitalIO *output, NSError *error) {
        if(output) {
            if([[output value] isEqualToNumber:[[NSNumber alloc] initWithBool:TRUE]])
                [self.switch6 setOn:YES animated:YES];
            else
                [self.switch6 setOn:NO animated:NO];
        }
    }];
    
    
    [api getRelayOutputWithCompletionBlock:@11 completionHandler:^(SWGDigitalIO *output, NSError *error) {
        if(output) {
            if([[output value] isEqualToNumber:[[NSNumber alloc] initWithBool:TRUE]])
                [self.switch7 setOn:YES animated:YES];
            else
                [self.switch7 setOn:NO animated:NO];
        }
    }];
    
    [api getRelayOutputWithCompletionBlock:@3 completionHandler:^(SWGDigitalIO *output, NSError *error) {
        if(output) {
            if([[output value] isEqualToNumber:[[NSNumber alloc] initWithBool:TRUE]])
                [self.switch8 setOn:YES animated:YES];
            else
                [self.switch8 setOn:NO animated:NO];
        }
    }];
    
    [api getRelayOutputWithCompletionBlock:@2 completionHandler:^(SWGDigitalIO *output, NSError *error) {
        if(output) {
            if([[output value] isEqualToNumber:[[NSNumber alloc] initWithBool:TRUE]])
                [self.switch9 setOn:YES animated:YES];
            else
                [self.switch9 setOn:NO animated:NO];
        }
    }];
    
    [api getRelayOutputWithCompletionBlock:@8 completionHandler:^(SWGDigitalIO *output, NSError *error) {
        if(output) {
            if([[output value] isEqualToNumber:[[NSNumber alloc] initWithBool:TRUE]])
                [self.switch10 setOn:YES animated:YES];
            else
                [self.switch10 setOn:NO animated:NO];
        }
    }];
}

-(IBAction)switch1ValueChanged:(id)sender {
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    UISwitch* l_switch = (UISwitch*) sender;
    
    BOOL state = FALSE;
    if([l_switch isOn]) {
        state = TRUE;
    }
    
    [api setOutputRelayWithCompletionBlock:[[NSNumber alloc] initWithBool:state] position:@4
                         completionHandler:^(SWGDigitalIO *output, NSError *error) {
                             if(output) {
                                 NSLog(@"ok!");
                             }
                         }];
}


-(IBAction)switch2ValueChanged:(id)sender {
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    UISwitch* l_switch = (UISwitch*) sender;
    
    BOOL state = FALSE;
    if([l_switch isOn]) {
        state = TRUE;
    }
    
    [api setOutputRelayWithCompletionBlock:[[NSNumber alloc] initWithBool:state] position:@6
                         completionHandler:^(SWGDigitalIO *output, NSError *error) {
                             if(output) {
                                 NSLog(@"ok!");
                             }
                         }];
}


-(IBAction)switch3ValueChanged:(id)sender {
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    UISwitch* l_switch = (UISwitch*) sender;
    
    BOOL state = FALSE;
    if([l_switch isOn]) {
        state = TRUE;
    }
    
    [api setOutputRelayWithCompletionBlock:[[NSNumber alloc] initWithBool:state] position:@7
                         completionHandler:^(SWGDigitalIO *output, NSError *error) {
                             if(output) {
                                 NSLog(@"ok!");
                             }
                         }];
}


-(IBAction)switch4ValueChanged:(id)sender {
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    UISwitch* l_switch = (UISwitch*) sender;
    
    BOOL state = FALSE;
    if([l_switch isOn]) {
        state = TRUE;
    }
    
    [api setOutputRelayWithCompletionBlock:[[NSNumber alloc] initWithBool:state] position:@9
                         completionHandler:^(SWGDigitalIO *output, NSError *error) {
                             if(output) {
                                 NSLog(@"ok!");
                             }
                         }];
}


-(IBAction)switch5ValueChanged:(id)sender {
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    UISwitch* l_switch = (UISwitch*) sender;
    
    BOOL state = FALSE;
    if([l_switch isOn]) {
        state = TRUE;
    }
    
    [api setOutputRelayWithCompletionBlock:[[NSNumber alloc] initWithBool:state] position:@12
                         completionHandler:^(SWGDigitalIO *output, NSError *error) {
                             if(output) {
                                 NSLog(@"ok!");
                             }
                         }];
}


-(IBAction)switch6ValueChanged:(id)sender {
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    UISwitch* l_switch = (UISwitch*) sender;
    
    BOOL state = FALSE;
    if([l_switch isOn]) {
        state = TRUE;
    }
    
    [api setOutputRelayWithCompletionBlock:[[NSNumber alloc] initWithBool:state] position:@10
                         completionHandler:^(SWGDigitalIO *output, NSError *error) {
                             if(output) {
                                 NSLog(@"ok!");
                             }
                         }];
}


-(IBAction)switch7ValueChanged:(id)sender {
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    UISwitch* l_switch = (UISwitch*) sender;
    
    BOOL state = FALSE;
    if([l_switch isOn]) {
        state = TRUE;
    }
    
    [api setOutputRelayWithCompletionBlock:[[NSNumber alloc] initWithBool:state] position:@11
                         completionHandler:^(SWGDigitalIO *output, NSError *error) {
                             if(output) {
                                 NSLog(@"ok!");
                             }
                         }];
}


-(IBAction)switch8ValueChanged:(id)sender {
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    UISwitch* l_switch = (UISwitch*) sender;
    
    BOOL state = FALSE;
    if([l_switch isOn]) {
        state = TRUE;
    }
    
    [api setOutputRelayWithCompletionBlock:[[NSNumber alloc] initWithBool:state] position:@1
                         completionHandler:^(SWGDigitalIO *output, NSError *error) {
                             if(output) {
                                 NSLog(@"ok!");
                             }
                         }];
}


-(IBAction)switch9ValueChanged:(id)sender {
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    UISwitch* l_switch = (UISwitch*) sender;
    
    BOOL state = FALSE;
    if([l_switch isOn]) {
        state = TRUE;
    }
    
    [api setOutputRelayWithCompletionBlock:[[NSNumber alloc] initWithBool:state] position:@3
                         completionHandler:^(SWGDigitalIO *output, NSError *error) {
                             if(output) {
                                 NSLog(@"ok!");
                             }
                         }];
}


-(IBAction)switch10ValueChanged:(id)sender {
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    UISwitch* l_switch = (UISwitch*) sender;
    
    BOOL state = FALSE;
    if([l_switch isOn]) {
        state = TRUE;
    }
    
    [api setOutputRelayWithCompletionBlock:[[NSNumber alloc] initWithBool:state] position:@2
                         completionHandler:^(SWGDigitalIO *output, NSError *error) {
                             if(output) {
                                 NSLog(@"ok!");
                             }
                         }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
