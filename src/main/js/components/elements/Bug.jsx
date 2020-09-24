import React, {useState} from "react";
import {bugsDelete} from "../../restClient";
import {
    Badge,
    Button,
    ButtonGroup,
    Card,
    CardBody,
    CardText,
    Collapse,
    ListGroupItem,
} from "reactstrap";

export default function Bug(props) {
    const [bugs, setBugs] = props.bugs;
    const {bug, userId, projectId} = props;

    const deleteBug = () => bugsDelete(userId, projectId, bug.id, callback);
    const callback = () => setBugs(bugs.filter((b) => b.id !== bug.id));

    const [isOpen, setIsOpen] = useState(false);
    const toggle = () => setIsOpen(!isOpen);

    return (
        <React.Fragment>
            <ListGroupItem
                tag="a"
                href="#"
                action
                className="justify-content-between d-flex align-items-center mt-1"
                onClick={toggle}
            >
                <h5 className="mb-1">{bug.title}</h5>{" "}
                <Badge pill>{bug.fixStatus}</Badge>
            </ListGroupItem>
            <Collapse isOpen={isOpen}>
                <Card>
                    <CardBody>
                        <CardText>
                            <strong>Expected results: </strong> {bug.expectedResults}
                        </CardText>
                        <CardText>
                            <strong>Actual results: </strong> {bug.actualResults}
                            {bug.stepsToReproduce}
                        </CardText>
                        <CardText>
                            <strong>Steps to reproduce: </strong>
                            {bug.stepsToReproduce}
                        </CardText>
                        <ButtonGroup size="sm" className="float-right">
                            <Button onClick={deleteBug}>Delete</Button>
                        </ButtonGroup>
                    </CardBody>
                </Card>
            </Collapse>
        </React.Fragment>
    );
}
